(ns om-bootstrap.server
  (:require [cemerick.piggieback :as p]
            [clojure.java.io :as io]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [compojure.core :refer [GET defroutes]]
            [org.httpkit.server :refer [run-server]]
            [weasel.repl.websocket :as w]))

(defn repl!
  "Starts a Clojurescript repl."
  []
  (p/cljs-repl :repl-env (w/repl-env)))

(defroutes app-routes
  (route/files "/vendor" {:root "docs/vendor"})
  (route/files "/assets" {:root "docs/assets"})
  (GET "/*" [] (io/file "docs/index.html")))

(defonce server
  (atom nil))

(defn -main
  "Boots up a server that redirects everything to the client side."
  []
  (let [port (or (System/getenv "PORT") 8080)]
    (when-let [f @server]
      (println "Killing existing server.")
      (f))
    (reset! server (run-server (handler/api #'app-routes)
                               {:port port}))
    (println "Server started on port [" port "].")))
