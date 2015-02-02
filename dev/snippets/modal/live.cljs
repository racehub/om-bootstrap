#_
(:require [om-bootstrap.modal :as md])

(defn trigger [app-state owner]
  (reify
    om/IInitState
    (init-state [_]
      {:visible? false})
    om/IRender
    (render [_]
      (d/div
        (md/modal {:header        (d/h4 "This is a Modal")
                   :footer        (d/div (b/button {} "Save")
                                         (b/button {} "Send"))
                   :close-button? true
                   :visible?      (om/get-state owner :visible?)}
                  "This is in the modal body")
        (b/button {:bs-style "primary"
                   :bs-size "large"
                   :on-click (fn [_] (om/set-state! owner :visible? true))}
                  "Click to open Modal")))))

(om/build trigger {})