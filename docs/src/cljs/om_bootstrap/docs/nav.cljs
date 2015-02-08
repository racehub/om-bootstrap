(ns om-bootstrap.docs.nav
  (:require [om-bootstrap.nav :as n]
            [om-tools.dom :as d :include-macros true])
  (:import [goog.history Html5History]))

(def history
  "Instance of the HTML5 History class."
  (doto (Html5History.)
    (.setUseFragment false)
    (.setEnabled true)))

(defn client-nav!
  "This trick comes from here:
   https://github.com/theJohnnyBrown/matchcolor/blob/master/src/matchcolor/views.cljs.

   This function is meant to be used as the :on-click event of an
   anchor tag."
  [e]
  (.setToken history
             (-> e .-target (.getAttribute "href"))
             (-> e .-target .-title))
  (.preventDefault e))


(defn nav-main [active-page]
  (n/navbar
   {:component-fn (fn [opts & c]
                    (d/header opts c))
    :brand (d/a {:href ""
                 :on-click (fn [e]
                             (.preventDefault e)
                             (client-nav! e))}
                "Om Bootstrap")
    :static-top? true
    :class "bs-docs-nav"
    :role "banner"
    :toggle-nav-key 0}
   (n/nav {:class "bs-navbar-collapse"
           :role "navigation"
           :key 0
           :id "top"}
          (n/nav-item {:on-select (fn [& _]
                                    (.setToken history
                                               "getting-started"
                                               "Getting started"))
                       :href "getting-started"
                       :active? (= "getting-started" active-page)}
                      "Getting started")
          (n/nav-item {:title "Components"
                       :href "components"
                       :on-select (fn [& _]
                                    (.setToken history
                                               "components"
                                               "Components"))
                       :active? (= "components" active-page)}
                      "Components"))))
