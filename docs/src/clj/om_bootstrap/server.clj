(ns om-bootstrap.server
  (:gen-class)
  (:require [clojure.java.io :as io]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [compojure.core :refer [GET defroutes]]
            [hiccup.page :as h]
            [hiccup.element :as e]
            [org.httpkit.server :refer [run-server]]))

(defn mode
  "Returns the current server mode."
  []
  (or (keyword (System/getenv "RING_ENV"))
      :dev))

(defn dev? []
  (= :dev (mode)))

(defn clojurescript []
  (if (dev?)
    (list
     (h/include-js "static/generated/goog/base.js")
     (h/include-js "static/assets/main.js")
     (e/javascript-tag "goog.require(\"om_bootstrap.docs\");"))
    (h/include-js "static/assets/generated/om_bootstrap.js")))

(defn index []
  (h/html5
   [:head [:title "Om Bootstrap"]
    [:meta {:http-equiv "X-UI-Compatible" :content "IE=edge"}]
    [:meta {:name "viewport" :content "width=device-width, initial-scale=1.0"}]
    (h/include-css "static/vendor/bootstrap/bootstrap.css")
    (h/include-css "static/vendor/bootstrap/docs.css")
    (h/include-css "static/vendor/highlight/solarized.css")
    (h/include-css "static/assets/style.css")
    "<!--[if lt IE 9]>"
    (h/include-js "https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js")
    (h/include-js "https://oss.maxcdn.com/respond/1.4.2/respond.min.js")
    (h/include-js "http://code.jquery.com/jquery-1.11.1.min.js")
    (h/include-js "http://cdnjs.cloudflare.com/ajax/libs/es5-shim/3.4.0/es5-shim.js")
    (h/include-js "http://cdnjs.cloudflare.com/ajax/libs/es5-shim/3.4.0/es5-sham.js")
    "<![endif]-->"]
   [:body
    [:div#app]
    (h/include-js "static/vendor/highlight/highlight.pack.js")
    (clojurescript)]))

(defroutes app-routes
  (route/resources "/static")
  (GET "/*" [] (index)))

(defonce server
  (atom nil))

(defn -main
  "Boots up a server that redirects everything to the client side."
  []
  (let [port (or (when-let [port-env (System/getenv "PORT")]
                   (Integer/parseInt port-env))
                 8080)]
    (when-let [f @server]
      (println "Killing existing server.")
      (f))
    (reset! server (run-server (handler/api #'app-routes)
                               {:port port}))
    (println "Server started on port [" port "].")))
