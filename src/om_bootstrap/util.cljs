(ns om-bootstrap.util
  "Utilities for the om-bootstrap library."
  (:require [schema.core :as s])
  (:require-macros [schema.macros :as sm]))

(sm/defn some-valid-component? :- s/Bool
  [children]
  (some #(.isValidComponent js/React %) children))

(defn merge-props
  "Merges props, adding classes together with a space where necessary."
  [l r]
  (let [props (merge l r)]
    (if-let [r-class (:class r)]
      (assoc props :class (str (:class l) " " (:class r)))
      props)))

(sm/defn collectify :- [s/Any]
  [x :- s/Any]
  (if (sequential? x) x [x]))
