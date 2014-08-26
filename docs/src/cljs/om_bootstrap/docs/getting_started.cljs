(ns om-bootstrap.docs.getting-started
  "Code for the getting started page."
  (:require [om-bootstrap.docs.example :refer [->code-block]]
            [om-bootstrap.docs.shared :refer [page-header]]
            [om-tools.dom :as d :include-macros true]))

(defn getting-started-page []
  [(page-header
    {:title "Getting started"
     :subtitle "An overview of Om-Bootstrap and how to install and use."})
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
                   (d/p {:class "lead"} "Getting started!")
                   (d/h3 "Leiningen")
                   (d/p "Add the om-bootstrap dependency to your project.clj:")
                   (->code-block {:code "[racehub/om-bootstrap \"0.2.5\"]"}))
                  (d/div
                   {:class "bs-docs-section"}
                   (d/h2 {:id "browser-support"
                          :class "page-header"}
                         "Browser support")
                   (d/p "We aim to support all browsers supported by both "
                        (d/a
                         {:href "http://facebook.github.io/react/docs/working-with-the-browser.html#browser-support-and-polyfills"}
                         "React")
                        " and " (d/a {:href "http://getbootstrap.com/getting-started/#support"} "Bootstrap") ".")))))])
