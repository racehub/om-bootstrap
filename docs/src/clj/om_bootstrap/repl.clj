(ns om-bootstrap.repl
  (:require [cemerick.piggieback :as p]
            [weasel.repl.websocket :as w]))

(defn start-repl
  "Starts a Clojurescript"
  []
  (p/cljs-repl :repl-env (w/repl-env)))
