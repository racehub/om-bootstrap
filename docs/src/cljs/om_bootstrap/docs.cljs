(ns om-bootstrap.docs
  (:import goog.History)
  (:require [cljs.core.async :as a :refer [chan put!]]
            [goog.events :as ev]
            [om.core :as om :include-macros true]
            [om-bootstrap.button :as b]
            [om-bootstrap.grid :as g]
            [om-bootstrap.input :as i]
            [om-bootstrap.nav :as n]
            [om-bootstrap.random :as r]
            [om-tools.core :refer-macros [defcomponent defcomponentk]]
            [om-tools.dom :as d :include-macros true]
            [secretary.core :as route :include-macros true :refer [defroute]]
            [weasel.repl :as ws-repl])
  (:require-macros [cljs.core.async.macros :refer [go-loop]])
  (:import [goog.history EventType Html5History]))

;; ## Button Examples

(defn button-example []
  (b/toolbar {}
             (b/button {} "Default")
             (b/button {:bs-style "primary"} "Primary")
             (b/button {:bs-style "success"} "Success")
             (b/button {:bs-style "info"} "Info")
             (b/button {:bs-style "warning"} "Warning")
             (b/button {:bs-style "danger"} "Danger")
             (b/button {:bs-style "link"} "Link")))

(defn button-sizing []
  (d/div
   (b/toolbar {}
              (b/button {:bs-style "primary" :bs-size "large"} "Large button")
              (b/button {:bs-size "large"} "Large button"))
   (b/toolbar {}
              (b/button {:bs-style "primary" :bs-size "large"} "Default button")
              (b/button {:bs-size "large"} "Large button"))
   (b/toolbar {}
              (b/button {:bs-style "primary" :bs-size "large"} "Small button")
              (b/button {:bs-size "large"} "Large button"))
   (b/toolbar {}
              (b/button {:bs-style "primary" :bs-size "xsmall"}
                        "Extra small button")
              (b/button {:bs-size "large"} "Large button"))))

(defn button-block []
  (d/div
   (d/h3 "Button Examples")
   (button-example)
   (d/h3 "Button Sizing")
   (button-sizing)
   (d/p "Create block level buttons—those that span the full width of a parent— by adding the block prop.")
   (d/div {:class "well"
           :style {:max-width 400
                   :margin "0 auto 10px"}}
          (b/button {:bs-style "primary" :bs-size "large" :block? true}
                    "Block level button")
          (b/button {:bs-size "large" :block? true}
                    "Block level button"))
   (d/p "To set a buttons active state simply set the components active prop.")
   (b/toolbar {}
              (b/button {:bs-style "primary" :bs-size "large" :active? true}
                        "Primary button")
              (b/button {:bs-size "large" :bs-style "default" :active? true}
                        "Button"))
   (d/p "Make buttons look unclickable by fading them back 50%. To do
   this add the disabled attribute to buttons.")
   (b/toolbar {}
              (b/button {:bs-style "primary" :bs-size "large" :disabled? true}
                        "Primary button")
              (b/button {:bs-size "large" :bs-style "default" :disabled? true}
                        "Button"))
   (d/h3 "Button Groups")
   (d/p "Wrap a series of buttons in a button group.")
   (b/button-group {}
                   (b/button {} "Left")
                   (b/button {} "Middle")
                   (b/button {} "Right"))
   (d/p "Combine sets of buttongroups into a button toolbar for more
   complex components.")
   (b/toolbar {}
              (b/button-group {} (for [i (range 4)]
                                   (b/button {} (str (inc i)))))
              (b/button-group {} (for [i (range 4 7)]
                                   (b/button {} (str (inc i)))))
              (b/button-group {} (b/button {} 8)))

   (d/p "Instead of applying button sizing props to every button in a group, just add bs-size prop to the <ButtonGroup />.")
   (let [buttons (for [s ["Left" "Middle" "Right"]]
                   (b/button {} s))]
     (b/toolbar {}
                (b/toolbar {} (b/button-group {:bs-size "large"} buttons))
                (b/toolbar {} (b/button-group {} buttons))
                (b/toolbar {} (b/button-group {:bs-size "small"} buttons))
                (b/toolbar {} (b/button-group {:bs-size "xsmall"} buttons))))))

;; ## Jumbotron Examples

(defn jumbotron-example []
  (r/jumbotron {}
               (d/h1 "Hello, World!")
               (d/p "This is a simple hero unit, a simple jumbotron-style component for calling extra attention to featured content or information.")
               (d/p (b/button {:bs-style "primary"} "Learn More"))))

;; ## Label Examples

(defn label-examples []
  (d/div
   (d/h1 "Label " (r/label {} "New"))
   (d/h2 "Label " (r/label {} "New"))
   (d/h3 "Label " (r/label {} "New"))
   (d/h4 "Label " (r/label {} "New"))
   (d/h5 "Label " (r/label {} "New"))
   (d/p "Label " (r/label {} "New"))))

(defn label-variations []
  (d/div
   (r/label {:bs-style "default"} "Default")
   (r/label {:bs-style "primary"} "Primary")
   (r/label {:bs-style "success"} "Success")
   (r/label {:bs-style "info"} "Info")
   (r/label {:bs-style "warning"} "Warning")
   (r/label {:bs-style "danger"} "Danger")))

;; ## Well Examples

(def default-well
    (r/well {} "Look, I'm in a well!"))

(def optional-well-classes
  (d/div
   (r/well {:bs-size "large"} "Look, I'm in a large well!")
   (r/well {:bs-size "small"} "Look, I'm in a small well!")))

;; ## Page Header

(def header-example
  (r/page-header {} "Example page header "
                 (d/small "Subtext for header")))

;; ## Grid

(def grid-example
  ;; Clearly this doesn't give me the coloring I need, but it's a
  ;; start toward what the bootstrap docs page gives me.
  (d/div {:class "bs-example grids-examples"}
         (g/grid {}
                 (g/row {:class "show-grid"}
                        (g/col {:xs 12 :md 8}
                               (d/code {} "(g/col {:xs 12 :md 8})"))
                        (g/col {:xs 6 :md 4}
                               (d/code {} "(g/col {:xs 6 :md 4})")))
                 (g/row {:class "show-grid"}
                        (g/col {:xs 6 :md 4}
                               (d/code {} "(g/col {:xs 6 :md 4})"))
                        (g/col {:xs 6 :md 4}
                               (d/code {} "(g/col {:xs 6 :md 4})"))
                        (g/col {:xs 6 :md 4}
                               (d/code {} "(g/col {:xs 6 :md 4})")))
                 (g/row {:class "show-grid"}
                        (g/col {:xs 6 :xs-offset 6}
                               (d/code {} "(g/col {:xs 6 :xs-offset 6})")))
                 (g/row {:class "show-grid"}
                        (g/col {:md 6 :md-push 6}
                               (d/code {} "(g/col {:md 6 :md-push 6})"))
                        (g/col {:md 6 :md-pull 6}
                               (d/code {} "(g/col {:md 6 :md-push 6})"))))))

;; ## Tooltip

(def tooltip-example
  (d/div {:class "bs-example"}
         (d/div {:style {:height 50}}
                (r/tooltip {:placement "right"
                            :position-left 150
                            :position-top 50}
                           (d/strong "Holy guacamole!")
                           "Check this info."))))

(comment

  (def positioned-tooltip-example
    "Positioned tooltip component. (TODO: Needs overlay trigger to
   finish!)"
    (let [tooltip  (r/tooltip {}
                              (d/strong "Holy guacamole!")
                              "Check this info.")]
      (b/toolbar {}
                 (overlay-trigger {:placement "left" :overlay tooltip})
                 (overlay-trigger {:placement "top" :overlay tooltip})
                 (overlay-trigger {:placement "bottom" :overlay tooltip})
                 (overlay-trigger {:placement "right" :overlay tooltip}))))

  (defn link-with-tooltip
    [{:keys [tooltip href]} & children]
    (overlay-trigger {:placement "top"
                      :overlay (r/tooltip {} tooltip)
                      :delay-show 300
                      :delay-hide 150}
                     (d/a {:href href} children)))

  (def positioned-tooltip-with-copy
    (d/p {:class "muted" :style {:margin-bottom 0}}
         "Call me Ishmael. Some years ago - never mind how long "
         (link-with-tooltip {:tooltip "Probably about two." :href "#"} "precisely")
         " - having little or no money in my purse, and nothing particular to interest me on shore, I thought I would sail about a little and see the watery part of the world. It is a way I have of driving off the spleen and regulating the circulation. Whenever I find myself growing grim about the mouth; whenever it is a damp, drizzly "
         (link-with-tooltip {:tooltip "The eleventh month!" :href "#"} "November")
         " in my soul; whenever I find myself involuntarily pausing before coffin warehouses, and bringing up the rear of every funeral I meet; and especially whenever my hypos get such an upper hand of me, that it requires a strong moral principle to prevent me from deliberately stepping into the "
         (link-with-tooltip {:tooltip "A large alley or a small avenue." :href "#"} "street")
         "m and methodically knocking people's hats off - then, I account it high time to get to sea as soon as I can. This is my substitute for pistol and ball. With a philosophical flourish Cato throws himself upon his sword; I quietly take to the ship. There is nothing surprising in "
         (link-with-tooltip {:tooltip "The ship, that is." :href "#"} "this")
         ". If they but knew it, almost all men in their degree, some time or other, cherish very nearly the same feelings towards the ocean with me.")))

;; ## Alerts

(def alert-example
  "Basic alert styles:"
  (r/alert {:bs-style "warning"}
           (d/strong "Holy guacamole!")
           "Best check yo self, you're not looking too good."))

(comment
  "TODO: Closeable alerts, just pass in a onDismiss function."
  ;; Fill in.

  "TODO: Auto close after a set time with dismissAfter prop."
  ;; Fill in.
  )

;; ## Navs

(def nav-example
  (d/div
   (d/p "Navs come in two styles, pills and tabs.")
   (n/nav {:bs-style "pills"
           :on-select (fn [k _]
                        (js/alert (str "Selected" k)))}
          (n/nav-item {:key 1 :href "/home"} "nav-item 1 content")
          (n/nav-item {:key 2 :href "/home"} "nav-item 1 content")
          (n/nav-item {:key 3 :href "/home" :disabled? true} "nav-item 1 content"))))

;; ## Final Page Loading

(defcomponentk app
  "This is the top level component that renders the entire example
  docs page."
  []
  (render [_]
          (d/div
           (button-block)
           (d/h3 "Jumbotron")
           (jumbotron-example)
           (d/h3 "Label Examples")
           (label-examples)
           (d/h3 "Label Variations")
           (label-variations)
           (d/h3 "Well!")
           default-well
           optional-well-classes
           (d/h3 "Page Header")
           header-example
           (d/h3 "Grid")
           grid-example
           (d/h3 "Tooltip")
           tooltip-example
           (d/h3 "Positioned Tooltip (in progress)")
           (d/h3 "Alert")
           alert-example
           (d/h3 "Nav")
           nav-example
           (i/input
            {:type "text" :addon-before "$"
             :help "Label before the input field."})
           )))

(defonce app-state
  (atom {:text "Hi!"}))

(defn load-om [component state]
  (om/root component state
           {:target (. js/document (getElementById "app"))}))

;; ## Client Side Routing and Navigation

(defroute "/" []
  (load-om app app-state))

(defcomponentk four-oh-four []
  (render [_] (d/p {:class "inner cover"} "We're not home!!")))

(defroute "*" []
  (load-om four-oh-four (atom {})))

(def history
  "Instance of the HTML5 History class."
  (doto (Html5History.)
    (.setUseFragment false)
    (.setEnabled true)))

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
  (let [nav (listen history (.-NAVIGATE EventType))]
    (go-loop []
      (let [token (.-token (a/<! nav))]
        (route/dispatch! (str "/" token)))
      (recur))))

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

(defn on-load
  "Loading actions for the main docs page."
  []
  (route/dispatch! (-> js/window .-location .-pathname))
  (setup-app)
  (when-not (ws-repl/alive?)
    (ws-repl/connect "ws://localhost:9001" :verbose true)))

(on-load)
