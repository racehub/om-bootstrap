(ns om-bootstrap.docs.getting-started
  "Code for the getting started page."
  (:require [om-bootstrap.docs.example :refer [->code-block]]
            [om-bootstrap.docs.shared :refer [page-header]]
            [om-tools.dom :as d :include-macros true]))


(defn getting-started-page []
  [(page-header
    {:title "Getting started"
     :subtitle "An overview of how to install and use Om-Bootstrap."})
   (d/div {:class "container bs-docs-container"}
          (d/div
           {:class "row"}
           (d/div {:class "col-md-9"
                   :role "main"}
                  (d/div
                   {:class "bs-docs-section"}
                   (d/h2 {:id "setup"
                          :class "page-header"}
                         "Setup")
                   (d/p {:class "lead"} "Om-Bootstrap is a library that makes it easy to use "
                        (d/a {:href "http://getbootstrap.com"} "Bootstrap's") " fantastic CSS elements and grid layout with "
                        (d/a {:href "https://github.com/swannodette/om"} "Om")" and ClojureScript. This section describes how to configure Leiningen and the "
                        (d/a {:href "http://github.com/emezeske/lein-cljsbuild"} "lein-cljsbuild") " plugin to get your first Om-Bootstrap project started.")
                   (d/h3 "Leiningen")
                   (d/p "You'll need " (d/a {:href "http://leiningen.org/"} "Leiningen")
                        " installed to build your Clojurescript project. Your " (d/code "project.clj")
                        " should include Clojure, ClojureScript, Om-Bootstrap and a supported version of "
                        (d/a {:href "https://github.com/swannodette/om"} "Om") ":")
                   (->code-block {:code "(defproject foo \"0.1.0\"
  ...
  :dependencies [[org.clojure/clojure \"1.6.0\"]
                 [org.clojure/clojurescript \"0.0-2322\"]
                 [racehub/om-bootstrap \"0.3.1\"]
                 [om \"0.7.3\"]]
  ...)"})
                   (d/p "Om-Bootstrap requires Om 0.7.0 or later, and has been tested against Bootstrap 3.1.0 and later. The "
                        (d/a {:href "https://github.com/racehub/om-bootstrap#supported-versions"} "Om-Bootstrap README")
                        " has more information on specific requirements and limitations.")
                   (d/p "For local development your "
                        (d/a {:href "http://github.com/emezeske/lein-cljsbuild"} "lein-cljsbuild")
                        "  settings should look something like this:")
                   (->code-block {:code ":cljsbuild {
  :builds [{:id \"dev\"
            :source-paths [\"src\"]
            :compiler {
              :output-to \"main.js\"
              :output-dir \"out\"
              :optimizations :none
              :source-map true}}]}"})
                   (d/p "Your local development markup should look like the following:")
                   (->code-block {:code "<html>
  <head>
    <link href=\"https://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css\"
          rel=\"stylesheet\"/>
  </head>
  <body>
    <div id=\"my-app\"></div>
    <script src=\"http://fb.me/react-0.11.1.js\"></script>
    <script src=\"out/goog/base.js\" type=\"text/javascript\"></script>
    <script src=\"main.js\" type=\"text/javascript\"></script>
    <script type=\"text/javascript\">goog.require(\"main.core\");</script>
  </body>
</html>"})
                   (d/p "(Note that you can replace the Bootstrap version here with your own.)
                         This markup assumes that you've placed your initial Clojurescript code
                         in a namespace called " (d/code "main.core")
                         ". Adjust accordingly.")
                   (d/p "For production, your "
                        (d/a {:href "http://github.com/emezeske/lein-cljsbuild"} "lein-cljsbuild")
                        "  settings should look like this:")
                   (->code-block {:code ":cljsbuild {
  :builds [{:id \"release\"
            :source-paths [\"src\"]
            :compiler {
              :output-to \"main.js\"
              :optimizations :advanced
              :pretty-print false
              :preamble [\"react/react.min.js\"]
              :externs [\"react/externs/react.js\"]}}]}"})
                   (d/p "This will generate a single file called " (d/code "main.js") "."
                        "Your production markup should look something like this:")
                   (->code-block {:code "<html>
  <head>
    <link href=\"https://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css\"
          rel=\"stylesheet\"/>
  </head>
  <body>
    <div id=\"my-app\"></div>
    <script src=\"main.js\" type=\"text/javascript\"></script>
  </body>
</html>"}))
                  (d/div
                   {:class "bs-docs-section"}
                   (d/h2 {:id "browser-support"
                          :class "page-header"}
                         "Browser support")
                   (d/p "We aim to support all browsers supported by both "
                        (d/a {:href "http://facebook.github.io/react/docs/working-with-the-browser.html#browser-support-and-polyfills"} "React")
                        " and "
                        (d/a {:href "http://getbootstrap.com/getting-started/#support"} "Bootstrap") ".")
                   (d/p "React requires "
                        (d/a {:href "http://facebook.github.io/react/docs/working-with-the-browser.html#browser-support-and-polyfills"}
                             "polyfills for non-ES5 capable browsers."))))))])
