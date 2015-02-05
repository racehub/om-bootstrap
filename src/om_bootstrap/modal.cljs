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
    {:visible? (get-in (om/get-props owner) [:opts :visible?])})
  (will-receive-props [this next-props]
    (let [last-props (om/get-props owner)
          last-visible? (om/get-state owner [:visible?])
          next-visible? (get-in next-props [:opts :visible?])]
      (when (not= last-visible? next-visible?)
        (om/set-state! owner [:visible?] next-visible?))))
  (render [_]
    (let [{:keys [opts children]} (om/get-props owner)
          [bs props] (t/separate Modal opts {:bs-class "modal"})
          classes {:modal true
                   :fade true
                   :in (om/get-state owner [:visible?])}]
      (d/div (u/merge-props props
                            {:class (d/class-set classes)})
        (d/div {:class "modal-dialog"}
          (d/div {:class "modal-content"}
            (d/div {:class "modal-header"}
              (when (:close-button? bs)
                (d/button {:type         "button"
                           :class        "close"
                           :aria-hidden  true
                           :on-click (fn [_] (om/set-state! owner [:visible?] false))}
                          "×"))
              (u/clone-with-props (:header bs) {:class "modal-title"}))
            (d/div {:class "modal-body"}
              children)
            (d/div {:class "modal-footer"}
              (:footer bs))))))))

(sm/defn modal
  [opts :- Modal & children]
  (->modal* {:opts opts :children children}))
