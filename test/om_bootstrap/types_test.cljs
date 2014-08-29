(ns om-bootstrap.types-test
  (:require [cemerick.cljs.test
             :include-macros true
             :refer [deftest is]]
            [om-bootstrap.types :as t]
            [schema.core :as s]))

(deftest schema-keys-test
  (is (= #{:a :b :c}
         (set (t/schema-keys
               {:a "a!"
                (s/optional-key :b) "b!"
                (s/optional-key :c) "b!"})))
      "Schema keys returns all keys with optionals unwrapped."))

(deftest bootstrap-test
  (let [attrs {:first-key "ay"
               :second-key "bee"}]
    (is (= (t/at-least
            (merge t/BootstrapClass attrs))
           (t/bootstrap attrs))
        "Usually, t/bootstrap just tacks on some extra optional fields
        and an s/Any s/Any pair so that extra items won't fail
        validation.")

    (is (= (t/at-least
            {:bs-style (s/enum "cake" "face")
             (s/optional-key :bs-size) t/BSSize
             (s/optional-key :bs-class) t/BSClass})
           (t/bootstrap {:bs-style (s/enum "cake" "face")}))
        "If the input schema contains one of the optional keys, its
        value will override the default optional key.")))

(deftest separate-test
  (is (= (t/separate t/BootstrapClass
                     {:bs-class "face" :a "b"})
         [{:bs-class "face"} {:a "b"}]))

  (is (= (t/separate t/BootstrapClass
                     {:bs-class "face" :a "b"}
                     {:bs-style "ace" :bs-class "one" :v "d"})
         [{:bs-style "ace", :bs-class "face"} {:v "d", :a "b"}])))
