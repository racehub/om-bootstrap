(ns om-bootstrap.mixins
  (:require [cljs.core.async :as a :refer [put!]]
            [om.core :as om]
            [om-tools.mixin :refer-macros [defmixin]]
            [schema.core :as s])
  (:require-macros [schema.macros :as sm]))

;; ## Listener Mixin

(sm/defn event-listener :- (sm/=> s/Any)
  "Registers the callback on the supplied target for events of type
   `event-type`. Returns a function of no arguments that, when called,
   unregisters the callback."
  [target :- s/Any
   event-type :- s/Str
   callback :- (sm/=> s/Any s/Any)]
  (cond (.-addEventListener target)
        (do (.addEventListener target event-type callback false)
            (fn [] (.removeEventListener target event-type callback false)))

        (.-attachEvent target)
        (let [event-type (str "on" event-type)]
          (.attachEvent target event-type callback)
          (fn [] (.detachEvent target event-type callback)))
        :else (fn [])))

(defmixin set-listener-mixin
  "Handles a sequence of listeners for the component, and removes them
   from the document when the component is unmounted."
  (will-mount [owner] (set! (.-listeners owner) #js []))
  (will-unmount [owner] (.. owner -listeners (map #(%))))
  (set-listener [owner target event-type callback]
                (let [remove-fn (event-listener target event-type callback)]
                  (.push (.-listeners owner) remove-fn))))

;; ## Timeout Mixin

(defmixin set-timeout-mixin
  "Handles a sequence of timeouts for the component, and removes them
   from the document when the component is unmounted."
  (will-mount [owner] (set! (.-timeouts owner) #js []))
  (will-unmount [owner]
                (.. owner -timeouts (map #(js/clearTimeout %))))
  (set-timeout [owner f timeout]
               (let [timeout (js/setTimeout f timeout)]
                 (.push (.-timeouts owner) timeout))))
