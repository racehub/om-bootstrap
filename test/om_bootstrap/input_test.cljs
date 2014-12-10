(ns om-bootstrap.input-test
  (:require [cemerick.cljs.test :as t
             :refer-macros [deftest is use-fixtures]]
            [schema.test :as st]))

(use-fixtures :once st/validate-schemas)

(deftest numeric-equal-test
  (is (= 1 1) "One equals one!"))
