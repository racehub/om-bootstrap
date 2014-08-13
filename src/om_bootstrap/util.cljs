(ns om-bootstrap.util
  "Utilities for the om-bootstrap library."
  (:require [schema.core :as s])
  (:require-macros [schema.macros :as sm]))

(defn merge-with-fns
  "Returns a map that consists of the rest of the maps conj-ed onto
  the first.  If a key occurs in more than one map, the mapping(s)
  from the latter (left-to-right) will be combined with the mapping in
  the result by looking up the proper merge function and in the
  supplied map of key -> merge-fn and using that for the big merge. If
  a key doesn't have a merge function, the right value wins (as with
  merge)."
  [k->fn maps]
  (letfn [(merge-entry [m e]
            (let [k (key e) v (val e)]
              (if-let [f (and (contains? m k)
                              (k->fn k))]
                (assoc m k (f (get m k) v))
                (assoc m k v))))
          (merge [m1 m2]
            (reduce merge-entry (or m1 {}) (seq m2)))]
    (reduce merge {} maps)))

(sm/defn collectify :- [s/Any]
  [x :- s/Any]
  (if (sequential? x) x [x]))

;; ## Reactish Utilities
;;
;; Some of these are rewritten from various React addons.

(sm/defn some-valid-component? :- s/Bool
  "Returns true if the supplied argument is a valid React component,
  false otherwise."
  [children]
  (boolean
   (some #(.isValidComponent js/React %) children)))

(def react-merges
  "Map of React keyword to a custom function for its merge."
  (let [merge-class (fn [l r] (str l " " r))
        empty-fn (fn [_ _] nil)]
    {:className merge-class
     :class merge-class
     :style merge
     :children empty-fn
     :key empty-fn
     :ref empty-fn}))

(defn merge-props
  "Merges two maps that represent react properties. Merges occur
  according to the functions defined in `react-merges`."
  [& prop-maps]
  (letfn [(react-merge [xs]
            (merge-with-fns react-merges xs))
          (normalize-class [m]
            (if (contains? m :class)
              (react-merge [(dissoc m :class) {:className (:class m)}])
              m))]
    (react-merge
     (map normalize-class prop-maps))))

(defn clone-with-props
  "Do a shallow copy of component and merge any props provided by
  extraProps. Props are merged in the same manner as merge-props, so
  props like :class will be merged intelligently."
  ([child]
     (clone-with-props child {}))
  ([child extra-props]
     (if (empty? extra-props)
       (.constructor child (.-props child))
       (let [props (js->clj (.-props child) :keywordize-keys true)
             new-props (merge (merge-props props extra-props)
                              (when-let [children (:children props)]
                                {:children children}))]
         (.constructor child (clj->js new-props))))))
