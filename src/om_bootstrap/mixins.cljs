(ns om-bootstrap.mixins
  (:require [om.core :as om]
            [om-tools.mixin :refer-macros [defmixin]]))

;; ## Fade

(defn get-fades [e]
  (.querySelectorAll (om/get-node e) ".fade"))

(defn fade-in
  "The idea here is to grab all components with a `fade` class and add
  the `in` class to them."
  [e]
  (doseq [elem (get-fades e)]
    ;; In the react-bootstrap example, they directly set the className
    ;; property here.
    (om/update-state! elem :className (fn [s] (str s " in")))))

(defn fade-out-end [e]
  ;; Select the fadeout element and remove it from its parent.
  )

(defn fade-out
  "Removes the `in` class from all child elements with a `fade` class."
  [e]
  ;; TODO: Make this accessible by ID.
  (let [fade-out-el (.querySelectorAll "#fadeout")]
    (doseq [e (.querySelectorAll ".fade.in")]
      ;; TODO: Use a better method for removing and adding
      ;; classes. This is janky. BUT, we still want to avoid
      ;; dependencies.
      ;;
      ;; check om-tools for deps.
      (om/update-state! e :className #(.replace % #"\bin\b" "")))
    (js/setTimeout fade-out-end 300)
    ))


(defmixin fade-mixin
  (did-mount [owner]
             ;; Not totally sure what's going on here; I THINK that
             ;; we're checking if the page has been loaded yet, and
             ;; then triggering a small delay before we fade all
             ;; components in.
             (when (.-querySelectorAll js/document)
               ;; Firefox needs delay for transition to be triggered
               (js/setTimeout #(fade-in owner) 20)))
  (will-unmount [owner]
                (when-let [els (not-empty (get-fades owner))]
                  ;; TODO: Add an ID?
                  (let [node (om/get-node owner)
                        fade-out-el (.createElement js/document "div")]
                    (.appendChild js/document.body fade-out-el)
                    (.appendChild fade-out-el (.cloneNode node  true))
                    ;; Firefox needs delay for transition to be triggered
                    (js/setTimeout fade-out 20)))))

;; ## Listener

(defmixin set-listener-mixin
  "Handles a sequence of listeners for the component, and removes them
   from the document when the component is unmounted.

   TODO: Do we want to use this with something other than js/document?"
  (will-mount [owner] (set! (.-listeners owner) #js []))
  (will-unmount [owner] (.. owner -listeners (map #(.remove %))))
  (set-listener [owner k c]
                (let [listener (.listen js/React.EventListener js/document k #(put! c %))]
                  (.push (.-listeners owner) listener))))

(defmixin set-timeout-mixin
  "Handles a sequence of timeouts for the component, and removes them
   from the document when the component is unmounted.

   TODO: Do we want to use this with something other than js/document?"
  (will-mount [owner] (set! (.-timeouts owner) #js []))
  (will-unmount [owner] (.. owner -timeouts (map #(.clearTimeout %))))
  (set-listener [owner f timeout]
                (let [timeout (.setTimeout f timeout)]
                  (.push (.-timeouts owner) timeout))))
