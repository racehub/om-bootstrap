(ns om-bootstrap.docs.footer
  (:require [om-tools.dom :as d :include-macros true]))

(defn footer []
  (d/footer
   {:class "bs-docs-footer" :role "contentinfo"}
   (d/div
    {:class "container"}
    (d/div
     {:class "bs-docs-social"}
     (d/ul
      {:class "bs-docs-social-buttons"}
      (d/li
       (d/iframe
        {:class "github-btn"
         :width 92
         :height 20
         :title "Star on Github"
         :src "http://ghbtns.com/github-btn.html?user=racehub&repo=om-bootstrap&type=watch&count=true"}))
      (d/li
       (d/iframe
        {:class "github-btn"
         :width 92
         :height 20
         :title "Fork on Github"
         :src "http://ghbtns.com/github-btn.html?user=racehub&repo=om-bootstrap&type=fork&count=true"}))))
    (d/p
     "Code licensed under "
     (d/a
      {:href "https://github.com/racehub/om-bootstrap/blob/master/LICENSE"
       :target "_blank"} "MIT") ".")
    (d/ul
     {:class "bs-docs-footer-links muted"}
     (d/li
      (d/a {:href "https://github.com/racehub/om-bootstrap"}
           "GitHub"))
     (d/li "·")
     (d/li
      (d/a {:href
            "https://github.com/racehub/om-bootstrap/issues?state=open"}
           "Issues"))
     (d/li "·")
     (d/li
      (d/a {:href "https://github.com/racehub/om-bootstrap/releases"}
           "Releases"))))))
