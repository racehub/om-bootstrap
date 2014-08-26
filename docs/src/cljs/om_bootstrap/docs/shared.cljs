(ns om-bootstrap.docs.shared
  (:require [om-tools.dom :as d :include-macros true]))

(defn page-header [{:keys [title subtitle]}]
  (d/div {:class "bs-docs-header" :id "content"}
         (d/div {:class "container"}
                (d/h1 title)
                (when subtitle
                  (d/p subtitle)))))

(defn four-oh-four []
  [(page-header {:title "404, Batman!"})
   (d/div {:class "container bs-docs-container"}
          (d/div
           {:class "row"}
           (d/div
            {:class "col-md-9" :role "main"}
            (d/div
             {:class "bs-docs-section"}
             (d/h2 {:class "page-header"}
                   "Page Not Found :(")
             (d/p "Try one of the links at the top to get some content, yo.")))))])
