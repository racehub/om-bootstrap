(ns om-bootstrap.docs.home
  "Code for the landing page."
  (:require [om-bootstrap.docs.shared :refer [page-header]]
            [om-tools.dom :as d :include-macros true]))

(defn home-page []
  (d/main {:class "bs-docs-masthead"
           :id "content"
           :role "main"}
          (d/div {:class "container"}
                 (d/span {:class (d/class-set {:bs-docs-booticon true
                                               :bs-docs-booticon-lg true
                                               :bs-docs-booticon-outline true})})
                 (d/p {:class "lead"}
                      "The most popular front-end framework, rebuilt for Om."))))
