(ns om-bootstrap.panel
  (:require [om.core :as om]
            [om-bootstrap.mixins :as m]
            [om-bootstrap.types :as t]
            [om-bootstrap.util :as u]
            [om-tools.core :refer-macros [defcomponentk]]
            [om-tools.dom :as d :include-macros true]
            [schema.core :as s])
  (:require-macros [schema.macros :as sm]))

(def Panel
  (t/bootstrap
   {:on-select (sm/=> s/Any s/Any)
    :header t/Renderable
    :footer t/Renderable
    (s/optional-key :list-group) t/Renderable}))

(sm/defn panel :- t/Component
  [opts :- Panel & children]
  (let [[bs props] (t/separate Panel opts {:bs-class "panel"
                                           :bs-style "default"})
        classes (assoc (t/bs-class-set bs)
                  :panel true)]
    (d/div (u/merge-props props {:class (d/class-set classes)})
           (when-let [header (:header bs)]
             (d/div {:class "panel-heading"}
                    (u/clone-with-props header {:class "panel-title"})))
           (when-not (= 0 (count (filter identity children)))
             (d/div {:class "panel-body" :ref "body"} children))
           (when-let [list-group (:list-group bs)]
             list-group)
           (when-let [footer(:footer bs)]
             (d/div {:class "panel-footer"} footer)))))
