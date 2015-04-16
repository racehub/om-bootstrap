#_
(:require [om-bootstrap.input :as i]
          [om-tools.core :refer-macros [defcomponentk]])

(defn validation-state
  "Returns a Bootstrap :bs-style string based on the supplied string
  length."
  [s]
  (let [l (count s)]
    (cond (> l 10) "success"
          (> l 5) "warning"
          (pos? l) "error"
          :else nil)))

(defn handle-change
  "Grab the input element via the `input` reference."
  [owner state]
  (let [node (om/get-node owner "input")]
    (swap! state assoc :text (.-value node))))

(defcomponentk example-input [owner state]
  (init-state [_] {:text ""})
  (render [_]
          (i/input
           {:feedback? true
            :type "text"
            :value (:text @state)
            :label "Working example with validation"
            :placeholder "Enter text"
            :help "Validates based on string length."
            :group-classname "group-class"
            :wrapper-classname "wrapper-class"
            :label-classname "label-class"
            :bs-style (validation-state (:text @state))
            :on-change #(handle-change owner state)})))

(->example-input {})
