(ns om-bootstrap.server
  (:gen-class)
  (:require [clojure.java.io :as io]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [compojure.core :refer [GET defroutes]]
            [org.httpkit.server :refer [run-server]]))

(defroutes app-routes
  (route/resources "/static")
  (GET "/*" [] (io/file "docs/index.html")))

(defonce server
  (atom nil))

(defn -main
  "Boots up a server that redirects everything to the client side."
  []
  (let [port (or (Integer/parseInt (System/getenv "PORT"))
                 8080)]
    (when-let [f @server]
      (println "Killing existing server.")
      (f))
    (reset! server (run-server (handler/api #'app-routes)
                               {:port port}))
    (println "Server started on port [" port "].")))

;; TODO: Check out history.js for supporting the html5 pushstate stuff
;; on different browsers.
