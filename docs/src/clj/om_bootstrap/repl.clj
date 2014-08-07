(ns om-bootstrap.repl
  (:require [cemerick.piggieback :as p]
            [weasel.repl.websocket :as w]))

(defn -main []
  (p/cljs-repl :repl-env (w/repl-env)))
