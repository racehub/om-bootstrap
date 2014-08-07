(ns om-bootstrap.docs
  (:require [om.core :as om]
            [om-tools.core :refer-macros [defcomponent defcomponentk]]
            [om-tools.dom :as d :include-macros true]
            [weasel.repl :as ws-repl]))

(defonce app-state
  (atom {:text "Hi!"}))

(defcomponentk app [[:data text]]
  (render [_] (d/h1 {} text)))

(when-not (ws-repl/alive?)
  (ws-repl/connect "ws://localhost:9001" :verbose true))

(om/root app app-state
         {:target (. js/document (getElementById "app"))})
