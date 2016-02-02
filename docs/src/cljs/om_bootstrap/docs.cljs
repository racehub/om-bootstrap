(ns om-bootstrap.docs
  (:require [cljs.core.async :as a :refer [chan put!]]
            [goog.events :as ev]
            [om.core :as om :include-macros true]
            [om-bootstrap.docs.footer :refer [footer]]
            [om-bootstrap.docs.nav :as n]
            [om-bootstrap.docs.components :refer [components-page]]
            [om-bootstrap.docs.getting-started :refer [getting-started-page]]
            [om-bootstrap.docs.home :refer [home-page]]
            [om-bootstrap.docs.shared :refer [four-oh-four]]
            [om-tools.core :refer-macros [defcomponentk]]
            [om-tools.dom :as d :include-macros true]
            [secretary.core :as route :refer-macros [defroute]])
  (:require-macros [cljs.core.async.macros :refer [go-loop]])
  (:import [goog.history EventType]))

(defn shell [active-page guts]
  (d/div {}
         (n/nav-main active-page)
         guts
         (footer)))

(defcomponentk app
  "This is the top level component that renders the entire example
  docs page."
  [[:data active-page]]
  (render [_]
          (shell active-page
                 (case active-page
                   "not-found" (four-oh-four)
                   "root" (home-page)
                   "getting-started" (getting-started-page)
                   "components" (components-page)))))

(defn load-om [component state]
  (om/root component state
           {:target (. js/document (getElementById "app"))}))

;; ## Client Side Routing and Navigation

(defroute "/" []
  (load-om app {:active-page "root"}))

(defroute "/getting-started" []
  (load-om app {:active-page "getting-started"}))

(defroute "/components" []
  (load-om app {:active-page "components"}))

(defroute "*" []
  (load-om app {:active-page "not-found"}))

(defn listen
  "Registers a listener of type `type` on the supplied
  element. Returns a channel that contains events."
  [el type]
  (let [out (chan)]
    (ev/listen el type (fn [e] (put! out e)))
    out))

(defn setup-app
  "Sets up an event loop that listens for client side "
  []
  (let [nav (listen n/history (.-NAVIGATE EventType))]
    (go-loop []
      (let [token (.-token (a/<! nav))]
        (route/dispatch! (str "/" token)))
      (recur))))

(defn on-load
  "Loading actions for the main docs page."
  []
  (route/dispatch! (-> js/window .-location .-pathname))
  (setup-app))

(on-load)
