(ns om-bootstrap.grid
  "Grid, Row, Col."
  (:require [om-bootstrap.types :as t]
            [om-bootstrap.util :as u]
            [om-tools.dom :as d :include-macros true]
            [schema.core :as s])
  (:require-macros [schema.core :as sm]))

;; ## Schema

(def Grid
  (t/bootstrap
   {(s/optional-key :fluid?) s/Bool}))

(def col-keys
  #{:xs :sm :md :lg
    :xs-offset :sm-offset :md-offset :lg-offset
    :xs-push :sm-push :md-push :lg-push
    :xs-pull :sm-pull :md-pull :lg-pull})

(def Col
  (t/bootstrap
   (-> (map s/optional-key col-keys)
       (zipmap (repeat s/Int)))))

;; ## Code
;;
;; TODO: Do we want a custom component class, like in react-bootstrap?
(sm/defn grid :- t/Component
  "Generates a wrapper for a bootstrap grid."
  [opts :- Grid & children]
  (let [[bs props] (t/separate Grid opts {})
        class (if (:fluid? bs)
                "container-fluid"
                "container")]
    (d/div (u/merge-props props {:class class})
           children)))

(sm/defn row :- t/Component
  "Generates a Bootstrap row element."
  [opts & children]
  (d/div (u/merge-props opts {:class "row"})
         children))

(sm/defn col :- t/Component
  "Generates a Bootstrap column element."
  [opts :- Col & children]
  (let [[bs props] (t/separate Col opts {})
        class (-> (map (fn [[k v]]
                         (str "col-" (name k) "-" v))
                       (select-keys bs col-keys))
                  (zipmap (repeat true))
                  (d/class-set))]
    (d/div (u/merge-props props {:class class})
           children)))
