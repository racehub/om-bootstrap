#_
(:require [om-bootstrap.button :as b]
          [om-bootstrap.mixins :as m]
          [om-tools.core :refer-macros [defcomponentk]]
          [om-tools.dom :as d :include-macros true])

(defcomponentk loading-button [state owner]
  (:mixins m/set-timeout-mixin)
  (init-state [_] {:loading? false})
  (render-state
   [_ {:keys [loading?]}]
   (let [toggle #(swap! state update-in [:loading?] not)
         handle-click (fn [e]
                        (toggle)
                        (.set-timeout owner toggle 2000))]
     (b/button {:bs-style "primary"
                :disabled? loading?
                :on-click (when-not loading?
                            handle-click)}
               (if loading?
                 "Loading..."
                 "Loading state")))))

(d/div (->loading-button {}))
