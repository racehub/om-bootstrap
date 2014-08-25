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

         ;; This is required to get around
         ;; https://github.com/Prismatic/om-tools/issues/29.
         set-timeout (aget owner "set_timeout")
         handle-click (fn [e]
                        (toggle)
                        (set-timeout toggle 2000))]
     (b/button {:bs-style "primary"
                :disabled? loading?
                :on-click (when-not loading?
                            handle-click)}
               (if loading?
                 "Loading..."
                 "Loading state")))))

(->loading-button {})
