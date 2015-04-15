(ns om-bootstrap.table
  (:require [om.core :as om]
            [om-bootstrap.types :as t]
            [om-bootstrap.util :as u]
            [om-tools.dom :as d :include-macros true]
            [schema.core :as s])
  (:require-macros [schema.core :as sm]))

(def Table
  {(s/optional-key :striped?) s/Bool
   (s/optional-key :bordered?) s/Bool
   (s/optional-key :condensed?) s/Bool
   (s/optional-key :hover?) s/Bool
   (s/optional-key :responsive?) s/Bool})

(sm/defn table
  "Generates a Bootstrap table wrapper."
  [opts :- Table & children]
  (let [[bs props] (t/separate Table opts)
        klasses {:table true
                 :table-striped (:striped? opts)
                 :table-bordered (:bordered? opts)
                 :table-condensed (:condensed? opts)
                 :table-hover (:hover? opts)}
        props (u/merge-props props {:class (d/class-set klasses)})
        table (d/table props children)]
    (if (:responsive? opts)
      (d/div {:class "table-responsive"} table)
      table)))
