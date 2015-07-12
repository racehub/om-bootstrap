(ns om-bootstrap.panel
  (:require [om.core :as om]
            [om-bootstrap.mixins :as m]
            [om-bootstrap.types :as t]
            [om-bootstrap.util :as u]
            [om-tools.core :refer-macros [defcomponentk]]
            [om-tools.dom :as d :include-macros true]
            [schema.core :as s :include-macros true]))

;; TODO: Dropdown functionality is NOT there yet, so :on-select is
;; ignored (https://github.com/racehub/om-bootstrap/issues/17)

(def Panel
  (t/bootstrap
   {(s/optional-key :on-select) (s/=> s/Any s/Any)
    (s/optional-key :header) t/Renderable
    (s/optional-key :footer) t/Renderable
    (s/optional-key :list-group) t/Renderable
    (s/optional-key :collapsible?) s/Bool
    (s/optional-key :collapsed?) s/Bool}))

(declare ->collapsible-panel*)

(s/defn panel :- t/Component
  [opts :- Panel & children]
  (let [[bs props] (t/separate Panel opts {:bs-class "panel"
                                           :bs-style "default"})
        classes (assoc (t/bs-class-set bs) :panel true)]
    (if (:collapsible? bs)
      (->collapsible-panel* {:opts     (dissoc opts :collapsible?)
                            :children children})
      (d/div (u/merge-props props {:class (d/class-set classes)})
             (when-let [header (:header bs)]
               (d/div {:class "panel-heading"}
                      (u/clone-with-props header {:class "panel-title"})))
             (when-not (= 0 (count (filter identity children)))
               (d/div {:class (str "panel-body" (when (:collapsed? bs) " collapse"))
                       :ref   "body"}
                      children))
             (when-let [list-group (:list-group bs)]
               list-group)
             (when-let [footer (:footer bs)]
               (d/div {:class "panel-footer"} footer))))))

;; ## Collapsible Panel

(defcomponentk collapsible-panel*
  "Generates a collapsible panel component resposible for its own toggled state.
   The :collapsed? state is handled through a collapsible mixin."
  [owner state]
  (:mixins m/collapsible-mixin)
  (render [_]
    (let [{:keys [opts children]} (om/get-props owner)
          is-collapsed? ((aget owner "isPanelCollapsed") owner)
          toggle! (fn [_] ((aget owner "toggleCollapsed") owner) false)
          collapsible-header (d/h4
                               (d/a {:href     "#"
                                     :on-click toggle!}
                                    (:header opts)))]
      (panel (u/merge-props opts {:header collapsible-header
                                  :collapsed? is-collapsed?})
             children))))
