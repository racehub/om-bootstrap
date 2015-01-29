(ns om-bootstrap.modal
  "IN PROGRESS work on a modal component. Depends on a fade mixin."
  (:require [om.core :as om]
            [om-bootstrap.types :as t]
            [om-tools.core :refer-macros [defcomponentk]]
            [om-tools.dom :as d :include-macros true]
            [schema.core :as s]
            [om-bootstrap.util :as u])
  (:require-macros [schema.macros :as sm]))

;; ## Schema

(def Modal
  "Options for the modal."
  {:header s/Any
   :footer s/Any
   (s/optional-key :keyboard?) s/Bool
   (s/optional-key :close-button?) s/Bool
   (s/optional-key :visible?) s/Bool
   (s/optional-key :animate?) s/Bool})

(defcomponentk modal*
  "Component that renders a Modal. Manages it's own toggle state"
  [owner state]
  (init-state [_]
    {:visible? (-> (om/get-props owner)
                   :opts
                   :visible?)})
  (render [_]
    (let [{:keys [opts children]} (om/get-props owner)
          [bs props] (t/separate Modal opts {:bs-class "modal"})
          visible? (om/get-state owner [:visible?])
          classes {:modal true
                   :fade true
                   :in visible?}]
      (d/div (u/merge-props props
                            {:class (d/class-set classes)})
             (d/div {:class "modal-dialog"}
                    (d/div {:class "modal-content"}
                           (d/div {:class "modal-header"}
                                  (when (:close-button? bs)
                                    (d/button {:type         "button"
                                               :class        "close"
                                               :aria-hidden  true
                                               :on-click (fn [_] (hide! owner))}
                                              "×"))
                                  (u/clone-with-props (:header bs) {:class "modal-title"}))
                           (d/div {:class "modal-body"}
                                  children)
                           (d/div {:class "modal-footer"}
                                        (:footer bs))))))))
(sm/defn modal
  [opts :- Modal & children]
  (->modal* {:opts opts :children children}))