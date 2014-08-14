(ns om-bootstrap.docs
  (:import goog.History)
  (:require [cljs.core.async :as a :refer [chan put!]]
            [goog.events :as ev]
            [om.core :as om :include-macros true]
            [om-bootstrap.docs.example :refer [bs-example ->example TODO]]
            [om-bootstrap.button :as b]
            [om-bootstrap.grid :as g]
            [om-bootstrap.input :as i]
            [om-bootstrap.mixins :as m]
            [om-bootstrap.nav :as n]
            [om-bootstrap.panel :as p]
            [om-bootstrap.random :as r]
            [om-bootstrap.util :as u]
            [om-tools.core :refer-macros [defcomponentk]]
            [om-tools.dom :as d :include-macros true]
            [schema.core :as s]
            [secretary.core :as route :include-macros true :refer [defroute]]
            [weasel.repl :as ws-repl])
  (:require-macros [cljs.core.async.macros :refer [go-loop]]
                   [om-bootstrap.macros :refer [slurp-example]]
                   [schema.macros :as sm])
  (:import [goog.history EventType Html5History]))

;; ## Helpers

(defn warning [title content]
  (d/div {:class "bs-callout bs-callout-warning"}
         (d/h4 title)
         content))

(defn section [id title & children]
  (d/div {:class "bs-docs-section"}
         (d/h1 {:id id :class "page-header"} title)
         children))

;; ## Button Examples

(defn button-options []
  [(d/h2 {:id "button-options"} "Options")
   (d/p "Use any of the available button style types to quickly create
   a styled button. Just modify the " (d/code ":bs-style") " prop.")
   (->example (slurp-example "button/types"))
   (warning
    "Button Spacing"
    (d/p "Because React doesn't output newlines between elements,
    buttons on the same line are displayed flush against each
    other. To preserve the spacing between multiple inline buttons,
    wrap your button group in " (d/code "b/toolbar") "."))])

(defn button-sizing []
  [(d/h2 "Sizes")
   (d/p "Fancy larger or smaller buttons? Add "
        (d/code ":bs-size large") ", "
        (d/code ":bs-size small") ", or "
        (d/code ":bs-size xsmall") " for additional sizes.")
   (->example (slurp-example "button/sizes"))
   (d/p "Create block level buttons — those that span the full width
   of a parent — by adding the " (d/code ":block? true") " prop.")
   (->example (slurp-example "button/block"))])

(defn button-states []
  [(d/h2 "Active state")
   (d/p "To set a button's active state, simply set the
   component's " (d/code ":active? true") " prop.")
   (->example (slurp-example "button/active"))
   (d/h2 "Disabled state")
   (d/p "Make buttons look unclickable by fading them back 50%. To do
   this, add the " (d/code ":disabled? true") "attribute to buttons.")
   (->example (slurp-example "button/disabled"))
   (warning
    "Event handler functionality not impacted"
    (d/p "This option will only change the button's appearance, not
    its functionality. Use custom logic to disable the effect of
    the " (d/code ":on-click") "handlers."))])

(defn button-groups []
  (section
   "btn-groups"
   ["Button groups " (d/small "ButtonGroup, ButtonToolbar")]
   (d/p {:class "lead"} "Group a series of buttons together on a
   single line with the button group.")
   (d/h3 "Basic example")
   (d/p "Wrap a series of " (d/code "b/button")
        "s together in a " (d/code "b/button-group") ".")
   (->example (slurp-example "button/group_basic"))
   (d/h3 "Button toolbar")
   (d/p "Combine sets of " (d/code "b/button-group")
        "s into a " (d/code "b/toolbar") " for more complex components.")
   (->example (slurp-example "button/toolbar_basic"))
   (d/h3 "Sizing")
   (d/p "Instead of applying button sizing props to every button in a group, add the "
        (d/code ":bs-size") " prop to the "
        (d/code "b/button-group")
        ".")
   (->example (slurp-example "button/group_sizes"))

   (d/h3 "Nesting")
   (d/p "You can place other button types within the <ButtonGroup /> like<DropdownButton />’s.")
   (TODO)

   (d/h3 "Vertical variation")
   (d/p "Make a set of buttons appear vertically stacked rather than
   horizontally. " (d/strong {:class "text-danger"} "Split button
   dropdowns are not supported here."))
   (d/p "Just add" (d/code ":vertical? true") "  to the " (d/code "b/button-group"))
   (TODO)

   (d/h3 "Justified button groups")
   (d/p "Make a group of buttons stretch at equal sizes to span the
   entire width of its parent. Also works with button dropdowns within
   the button group.")
   (warning
    "Style issues"
    (d/p "There are some issues and workarounds required when using
     this property, please see"
         (d/a {:href "http://getbootstrap.com/components/#btn-groups-justified"}
              "bootstrap's button group docs")
         " for more specifics."))
   (d/p "Just add " (d/code ":justified? true") " to
   the " (d/code "b/button-group") ".")
   (TODO)))

(defn button-dropdowns []
  (section
   "btn-dropdowns"
   "Button dropdowns"
   (d/h3 "Single button dropdowns (TODO)")
   (d/h3 "Split button dropdowns (TODO)")
   (d/h3 "Sizing (TODO)")
   (d/h3 "Dropup variation (TODO)")
   (d/h3 "Dropdown right variation (TODO)")))

(defn button-main []
  (section
   "buttons"
   ["Buttons " (d/small "Button")]
   (button-options)
   (button-sizing)
   (button-states)

   (d/h2 "Button tags")
   (d/p "The DOM element tag is chosen automatically for you based on
   the options you supply. Passing " (d/code ":href") " will result in
   the button using a " (d/code "<a />") " element. Otherwise,
   a " (d/code "<button />") " element will be used.")
   (->example (slurp-example "button/tag_types"))

   (d/h2 "Button loading state")
   (d/p "When activating an asynchronous action from a button it is a
   good UX pattern to give the user feedback as to the loading
   state. This can easily be done by updating your button's props from
   a state change like below.")
   (->example (slurp-example "button/loading"))))

(defn button-block []
  [(button-main)
   (button-groups)
   (button-dropdowns)])

;; ## Panel Examples

(defn panel-block []
  (section
   "panels"
   ["Panels " (d/small "Panel, PanelGroup, Accordion")]

   (d/h3 "Basic example")
   (d/p "By default, all the " (d/code "p/panel") " does is apply some
   basic border and padding to contain some content.")
   (->example (slurp-example "panel/basic"))

   (d/h3 "Panel with heading")
   (d/p "Easily add a heading container to your panel with
   the " (d/code ":header") " option.")
   (->example (slurp-example "panel/heading"))

   (d/h3 "Panel with footer")
   (d/p "Pass buttons or secondary text with
   the" (d/code ":footer") "option. Note that panel footers do not
   inherit colors and borders when using contextual variations as they
   are not meant to be in the foreground.")
   (->example (slurp-example "panel/footer"))

   (d/h3 "Contextual alternatives")
   (d/p "Like other components, make a panel more meaningful to a
   particular context by adding a " (d/code ":bs-style") " prop.")
   (->example (slurp-example "panel/contextual"))

   (d/h3 "Controlled PanelGroups")
   (d/p (d/code "p/panel-group") "s can be controlled by a parent
   component. The " (d/code ":active-key") " prop dictates which panel
   is open.")
   (TODO)

   (d/h3 "Accordions")
   (d/p (d/code "p/accordion") " aliases " (d/code "(d/panel-group
   {:accordion? true} ,,,)") ".")
   (TODO)))

;; ## Modal

(defn modal-block []
  (section
   "modals"
   ["Modals " (d/small "Modal")]
   (d/h3 "A static example")
   (d/p "A rendered modal with header, body, and set of actions in the footer.")
   (d/p "The header is added automatically if you pass in
   a " (d/code ":title") " option.")))

;; ## Label

(defn label-block []
  (section
   "labels" "Labels"
   (d/h3 "Example")
   (d/p "Create " (d/code "(r/label {} \"label\")") " to show
   highlight information.")
   (->example (slurp-example "label/basic"))

   (d/h3 "Available variations")
   (d/p "Add any of the below mentioned modifier classes to change the
   appearance of a label.")
   (->example (slurp-example "label/variations"))))

;; ## Badges

(defn badge-block []
  (section
   "badges"
   "Badges"
   (d/p "Easily highlight new or unread items by adding
   a " (d/code "r/badge") " to links, Bootstrap navs, and more.")
   (d/h3 "Example")
   (->example (slurp-example "badge"))))

;; ## Jumbotron

(defn jumbotron-block []
  (section
   "jumbotron" "Jumbotron"
   (d/p "A lightweight, flexible component that can optionally extend
   the entire viewport to showcase key content on your site.")
   (d/h3 "Example")
   (->example (slurp-example "jumbotron/basic"))))

;; ## Page Header

(defn header-block []
  (section
   "page-header"
   "Page Header"
   (d/p "A simple shell for an " (d/code "h1") " to appropriately
   space out and segment sections of content on a page. It can utilize
   the " (d/code "h1") "’s default " (d/code "small") " small element,
   as well as most other components (with additional styles).")
   (d/h3 "Example")
   (->example (slurp-example "page_header"))))

;; ## Well

(defn well-block []
  (section
   "wells"
   "Wells"
   (d/p "Use the well as a simple effect on an element to give it an inset effect.")
   (d/h3 "Default Wells")
   (->example (slurp-example "well/basic"))

   (d/h3 "Optional classes")
   (d/p "Control padding and rounded corners with two optional modifier classes.")
   (->example (slurp-example "well/sizes"))))

;; ## Grid

(def grid-example
  ;; Clearly this doesn't give me the coloring I need, but it's a
  ;; start toward what the bootstrap docs page gives me.
  (bs-example {:class "grids-examples"}
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
  (bs-example {:style {:height 50}}
              (r/tooltip {:placement "right"
                          :position-left 150
                          :position-top 50}
                         (d/strong "Holy guacamole!")
                         "Check this info.")))

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
  (bs-example
   (r/alert {:bs-style "warning"}
            (d/strong "Holy guacamole!")
            "Best check yo self, you're not looking too good.")))

(comment
  "TODO: Closeable alerts, just pass in a onDismiss function."
  ;; Fill in.

  "TODO: Auto close after a set time with dismissAfter prop."
  ;; Fill in.
  )

;; ## Popovers

(def popover-example
  (bs-example
   (d/div {:style {:height 120}}
          (r/popover {:placement "right"
                      :position-left 200
                      :position-top 50
                      :title "Popover right"}
                     "And here's some "
                     (d/strong "amazing")
                     " content. It's very engaging. Right?"))))

;; ## Navs

(def nav-example
  (let [on-select (fn [k _] (js/alert (str "Selected " k)))
        nav-example (fn [style]
                      (bs-example
                       (n/nav {:bs-style style}
                              (n/nav-item {:key 1 :href "/home" :active? true
                                           :on-select on-select}
                                          "nav-item 1 content")
                              (n/nav-item {:key 2 :href "/home"
                                           :on-select on-select}
                                          "nav-item 2 content")
                              (n/nav-item {:key 3 :href "/home" :disabled? true
                                           :on-select on-select}
                                          "nav-item 3 content"))))]
    (d/div
     (d/p "Navs come in two styles, pills:")
     (nav-example "pills")
     (d/p "And tabs:")
     (nav-example "tabs"))))

;; ## Final Page Loading

(defn sidebar []
  (d/div
   {:class "col-md-3"}
   (d/div {:class "bs-docs-sidebar hidden-print"
           :role "complementary"}
          (n/nav
           {:class "bs-docs-sidenav"}
           (n/nav-item {:href "#buttons"} "Buttons")
           (n/nav-item {:href "#panels"} "Panels")
           (n/nav-item {:href "#modals"} "Modals")
           (n/nav-item {:href "#tooltips"} "Tooltips")
           (n/nav-item {:href "#popovers"} "Popovers")
           (n/nav-item {:href "#progress"} "Progress bars")
           (n/nav-item {:href "navs"} "Navs")
           (n/nav-item {:href "#navbars"} "Navbars")
           (n/nav-item {:href "#tabs"} "Toggleable Tabs")
           (n/nav-item {:href "#pager"} "Pager")
           (n/nav-item {:href "#alerts"} "Alerts")
           (n/nav-item {:href "#carousels"} "Carousels")
           (n/nav-item {:href "#grids"} "Grids")
           (n/nav-item {:href "#listgroup"} "List group")
           (n/nav-item {:href "#labels"} "Labels")
           (n/nav-item {:href "#badges"} "Badges")
           (n/nav-item {:href "#jumbotron"} "Jumbotron")
           (n/nav-item {:href "#page-header"} "Page header")
           (n/nav-item {:href "#wells"} "Wells")
           (n/nav-item {:href "#glyphicons"} "Glyphicons")
           (n/nav-item {:href "#tables"} "Tables")
           (n/nav-item {:href "#input"} "Input"))
          (d/a {:class "back-to-top" :href "#top"} "Back to top"))))

(defcomponentk app
  "This is the top level component that renders the entire example
  docs page."
  []
  (render [_]
          (d/div
           (d/div {:class "bs-docs-header" :id "content"}
                  (d/div {:class "container"}
                         (d/h1 "Components")))
           (d/div
            {:class "container bs-docs-container"}
            (d/div
             {:class "row"}
             (d/div
              {:class "col-md-9" :role "main"}
              (d/div {:class "lead"}
                     "This page lists the Om-Bootstrap components. For each component, we provide:"
                     (d/ul
                      (d/li "Usage instructions")
                      (d/li "An example code snippet")
                      (d/li "The rendered result of the example snippet"))
                     "Click \"show code\" below the rendered component to reveal the snippet.")
              (button-block)
              (panel-block)
              (modal-block)
              (label-block)
              (badge-block)
              (jumbotron-block)
              (header-block)
              (well-block)

              (d/h3 "Grid")
              grid-example
              (d/h3 "Tooltip")
              tooltip-example
              (d/h3 "Positioned Tooltip (in progress)")
              (d/h3 "Alert")
              alert-example
              (d/h3 "Popover")
              popover-example
              (d/h3 "Nav")
              nav-example)
             (sidebar))))))

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
