(ns om-bootstrap.types-test
  (:require [om-bootstrap.types :as t]
            [cemerick.cljs.test
             :include-macros true
             :refer [deftest is]]))

(deftest separate-test
  (is (= (t/separate t/BootstrapClass
                     {:bs-class "face" :a "b"})
         [{:bs-class "face"} {:a "b"}]))

  (is (= (t/separate t/BootstrapClass
                     {:bs-class "face" :a "b"}
                     {:bs-style "ace" :bs-class "one" :v "d"})
         [{:bs-style "ace", :bs-class "face"} {:v "d", :a "b"}])))
