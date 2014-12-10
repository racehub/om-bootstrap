(ns om-bootstrap.util-test
  (:require [cemerick.cljs.test :as t
             :include-macros true
             :refer [deftest is are use-fixtures]]
            [om.core :as om]
            [om-bootstrap.button :refer [dropdown]]
            [om-bootstrap.util :as u]
            [om-tools.core :refer-macros [defcomponentk]]
            [om-tools.dom :as d :include-macros true]
            [schema.test :as st]))

;; ## Utilities (lifted from om-tools.dom-test)

(def +react-dom-prototype+ (.-prototype (js/React.DOM.span nil)))

(defn react-dom? [x]
  (and x (= (.-prototype x) +react-dom-prototype+)))

(defn props [el]
  (js->clj (.-props el) :keywordize-keys true))

(defn children [el]
  (:children (props el)))

(defn is=el [el1 el2]
  (is (= (.-tagName el1) (.-tagName el2)))
  (let [el1-props (props el1)
        el2-props (props el2)
        el1-children (:children el1-props)
        el2-children (:children el2-props)]

    (is (= (dissoc el1-props :children)
           (dissoc el2-props :children)))

    (cond
     (every? coll? [el1-children el2-children])
     (doseq [[c1 c2] (map vector (:children el1-props) (:children el2-props))]
       (is=el c1 c2))

     (every? react-dom? [el1-children el2-children])
     (is=el el1-children el2-children)

     :else (is (= el1-children el2-children)))))

;; ## Tests

(deftest merge-with-fns-test
  (is (= (u/merge-with-fns {:a +, :b -}
                           [{:a 1 :b 10}
                            {:a 2 :c "hi!"}
                            {:a 3 :b 5 :c "ho!"}])
         {:a 6, :b 5, :c "ho!"})
      "merge-with-fns: If the supplied function map has a function to
       merge with, use that. Otherwise, right wins."))

(deftest collectify-test
  "Collectify coerces items to collections. Because sets aren't
  ordered they get wrapped in their own fresh vector, just like maps."
  (are [coll result]
    (= result (u/collectify coll))
    '(1 2 3)    '(1 2 3)
    [5 5]       [5 5]
    "aaa"       ["aaa"]
    {:a 1 :b 2} [{:a 1 :b 2}]
    #{1 2 3}    [#{1 2 3}]))

(deftest valid-component-test
  (is (u/strict-valid-component? (d/div {} "Something.")))
  (is (not (u/strict-valid-component? "String!"))
      "Strings are valid components, but not strict valid
      components.")
  (is (and (u/valid-component? "face")
           (u/valid-component? 123123)
           (u/valid-component? (d/p "a paragraph.")))
      "Strings are numbers are valid components; react wraps them
      internally.")
  (is (not (u/valid-component? nil)))
  (is (u/some-valid-component? [1 2 nil]))
  (is (not (u/some-valid-component? [nil nil]))))

(deftest chain-fns-test
  (let [counter (atom 0)
        adder (fn [] (swap! counter inc))
        double-adder (u/chain-fns adder adder)]
    (is (zero? @counter) "The counter starts at zero.")
    (adder)
    (is (= 1 @counter)
        "Calling the basic adder functions increments the counter by 1.")
    (double-adder)
    (is (= 3 @counter)
        "The chained version executes the side effects of `adder`
        twice, incrementing the counter by two more.")))

(deftest om-component-test
  []
  (is (not (u/om-component? (d/p "Just a p"))))
  (is (u/om-component? (dropdown {:title "Title!"}))))

(deftest merge-props-test
  (is (= (u/merge-props {:face "cake" :class "first"}
                        {:cake "face" :className "second"})
         {:face "cake" :className "first second" :cake "face"})
      "When properties merge, they normalize :class -> :className and
      properly merge classes."))

(defcomponentk fake-div [[:data x y z]]
  (render [_] (d/div {} (str "I got these numbers: " x y z))))

(deftest clone-with-props-test
  "Clone with props clones both Om components and standard React
  components."
  (let [prop-map {:a "a" :b "b" :className "face"}]
    (is (= prop-map
           (-> (props (d/div prop-map "div."))
               (dissoc :children)))
        "Props returns the element's React props.")
    "Cloning the elements returns an element that acts the same."
    (is=el (d/div prop-map (d/p "Clever!"))
           (u/clone-with-props (d/div prop-map (d/p "Clever!"))))

    "Cloning with EXTRA props merges those props into the value that
    was cloned. Note that the classes merge together as expected."
    (is=el (d/div {:a "b" :extra "value" :class "cake walrus"}
                  (d/p "Clever!"))
           (-> (d/div {:a "b" :class "cake"} (d/p "Clever!"))
               (u/clone-with-props {:extra "value"
                                    :class "walrus"})))

    (is (= {:x "one" :y "two" :z "three"}
           (om/get-props (->fake-div {:x "one" :y "two" :z "three"})))
        "om/get-props returns the inner Om props, as expected. These
        are trapped inside the actual props in a field called
        __om_cursor.")

    (let [om-component (->fake-div {:x "one" :y "two" :z "three" :class "cake"})]
      "Cloning an Om component works too."
      (is=el om-component (u/clone-with-props om-component)))

    (is (= {:x "alpha", :y "two", :z "three", :className "cake walrus"}
           (-> (->fake-div {:x "one" :y "two" :z "three"
                            :class "cake"})
               (u/clone-with-props {:x "alpha", :class "walrus"})
               (om/get-props)))
        "Cloning an om component merges the extra properties into the
        cursor, NOT into the overall props.")

    (let [hut-the-vals (fn [m]
                         (->> (map (fn [[k v]]
                                     [k (str v "-hut!")])
                                   m)
                              (into {})))]
      (is (= {:x "one-hut!"
              :y "two-hut!"
              :z "three-hut!"
              :class "cake-hut!"}
             (-> (->fake-div {:x "one" :y "two" :z "three" :class "cake"})
                 (u/clone-with-props hut-the-vals)
                 (om/get-props)))
          "clone-with-props can take a function as well. This one adds
          hut! onto the end of all string values.

          The whole :class merging only comes into play if extra attrs
          contains a :class as well. :class is left alone here and not
          converted to :className."))))
