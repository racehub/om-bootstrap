(ns om-bootstrap.input-test
  "TODO: Fill in tests!"
  (:require [cemerick.cljs.test :as t :include-macros true :refer [deftest is use-fixtures]]
            [schema.test :as st]))

(use-fixtures :once st/validate-schemas)
