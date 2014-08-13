(ns om-bootstrap.util-test
  (:require [om-bootstrap.util :as u]
            [cemerick.cljs.test :as t
                :include-macros true
                :refer [deftest is use-fixtures]]
            [schema.test :as st]))

(deftest merge-with-fns-test
  (is (= (u/merge-with-fns {:a +, :b -}
                           {:a 1 :b 10}
                           {:a 2 :c "hi!"}
                           {:a 3 :b 5 :c "ho!"})
         {:a 6, :b 5, :c "ho!"})
      "merge-with-fns: If the supplied function map has a function to
       merge with, use that. Otherwise, right wins."))

(deftest merge-props-test
  (is (= (merge-props {:face "cake" :class "first"}
                      {:cake "face" :className "second"})
         {:face "cake" :className "first second" :cake "face"})
      "When properties merge, they normalize :class -> :className and
      properly merge classes."))


(deftest valid-component-test
  (is (true? (valid-component? 1)))
  (is (true? (valid-component? "string")))
  (is (false? (valid-component? null)))
  (is (true? (some-valid-component? [1 2 null])))
  (is (false? (some-valid-component? [null null]))))
