(ns om-bootstrap.docs.components
  "All components for the om-bootstrap documentation project."
  (:require [om.core :as om :include-macros true]
            [om-bootstrap.docs.example :refer [->example TODO]]
            [om-bootstrap.docs.shared :refer [page-header]]
            [om-bootstrap.button :as b]
            [om-bootstrap.grid :as g]
            [om-bootstrap.input :as i]
            [om-bootstrap.mixins :as m]
            [om-bootstrap.nav :as n]
            [om-bootstrap.panel :as p]
            [om-bootstrap.progress-bar :as pb]
            [om-bootstrap.random :as r]
            [om-bootstrap.table :refer [table]]
            [om-tools.core :refer-macros [defcomponentk]]
            [om-tools.dom :as d :include-macros true])
  (:require-macros [om-bootstrap.macros :refer [slurp-example]]))

;; ## Helpers

(defn info-callout [title content]
  (d/div {:class "bs-callout bs-callout-info"}
         (d/h4 title)
         content))

(defn warning [title content]
  (d/div {:class "bs-callout bs-callout-warning"}
         (d/h4 title)
         content))

(defn section [id title & children]
  (d/div {:class "bs-docs-section"}
         (d/h1 {:id id :class "page-header"} title)
         children))

;; ## Button

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
   ["Button groups " (d/small "button.cljs")]
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
   (d/p "You can place other button types within the "
        (d/code "b/button-group") ", like "
        (d/code "b/dropdown") "s.")
   (->example (slurp-example "button/group_nested"))

   (d/h3 "Vertical variation")
   (d/p "Make a set of buttons appear vertically stacked rather than
   horizontally. " (d/strong {:class "text-danger"} "Split button
   dropdowns are not supported here."))
   (d/p "Just add " (d/code ":vertical? true") "  to the " (d/code "b/button-group"))
   (->example (slurp-example "button/group_vertical"))

   (d/h3 "Justified button groups")
   (d/p "Make a group of buttons stretch at equal sizes to span the
   entire width of its parent. Also works with button dropdowns within
   the button group.")
   (warning
    "Style issues"
    (d/p "There are some issues and workarounds required when using
     this property, please see "
         (d/a {:href "http://getbootstrap.com/components/#btn-groups-justified"}
              "bootstrap's button group docs")
         " for more specifics."))
   (d/p "Just add " (d/code ":justified? true") " to
   the " (d/code "b/button-group") ".")
   (->example (slurp-example "button/group_justified"))))

(defn button-dropdowns []
  (section
   "btn-dropdowns"
   "Button dropdowns"
   (d/p {:class "lead"}
        "Use " (d/code "b/dropdown") " or " (d/code "b/split") "
        components to display a button with a dropdown menu.")

   (d/h3 "Single button dropdowns")
   (d/p "Create a dropdown button with the " (d/code "b/dropdown") " component.")
   (->example (slurp-example "button/dropdown_basic"))

   (d/h3 "Split button dropdowns")
   (d/p "Similarly, create split button dropdowns with
   the " (d/code "b/split") " component.")
   (->example (slurp-example "button/split_basic"))

   (d/h3 "Sizing")
   (d/p "Button dropdowns work with buttons of all sizes.")
   (->example (slurp-example "button/dropdown_sizes"))

   (d/h3 "Dropup variation")
   (d/p "Trigger dropdown menus that site above the button by adding
   the " (d/code ":dropup? true") " option.")
   (->example (slurp-example "button/split_dropup"))


   (d/h3 "Dropdown right variation")
   (d/p "Trigger dropdown menus that align to the right of the button
   using the " (d/code ":pull-right? true") " option.")
   (->example (slurp-example "button/split_right"))))

(defn button-main []
  (section
   "buttons"
   ["Buttons " (d/small "button.cljs")]
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

;; ## Panel

(defn panel-block []
  (section
   "panels"
   ["Panels " (d/small "panel.cljs")]

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

   (d/h3 "Panel with list group")
   (d/p "Put full-width list-groups in your panel with the " (d/code ":list-group") "option.")
   (->example (slurp-example "panel/list-group"))

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
   ["Modals " (d/small "modal.cljs (IN PROGRESS)")]
   (d/h3 "A static example")
   (d/p "A rendered modal with header, body, and set of actions in the footer.")
   (d/p "The header is added automatically if you pass in
   a " (d/code ":title") " option.")
   (TODO)

   (d/h3 "Live Demo")
   (d/p "Use " (d/code "overlay-trigger") " to create a real modal
   that's added to the document body when opened.")
   (TODO)

   (d/h3 "Custom trigger")
   (d/p "Use " (d/code "overlay-mixin") " in a custom component to
   manage the modal's state yourself.")
   (TODO)))

;; ## Tooltips

(defn tooltip-block []
  (section
   "tooltips"
   ["Tooltips " (d/small "random.cljs")]
   (d/h3 "Example tooltips")
   (d/p "Tooltip component.")
   (->example (slurp-example "tooltip/basic"))

   (d/p "Positioned tooltip component.")
   (TODO)

   (d/p "Positioned tooltip in copy.")
   (TODO)))

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

;; ## Popovers

(defn popover-block []
  (section
   "popovers"
   ["Popovers " (d/small "random.cljs")]
   (d/h3 "Example popovers")
   (d/p "Popovers component.")
   (->example (slurp-example "popover/basic"))

   (d/p "Popovers component.")
   (TODO)

   (d/p "Popovers scrolling.")
   (TODO)))

;; ## Progress Bars

(defn progress-bar-block []
  (section
   "progress"
   ["Progress bars " (d/small "progress_bar.cljs (IN PROGRESS)")]
   (d/p {:class "lead"}
        "Provide up-to-date feedback on the progress of a workflow or
        action with simple yet flexible progress bars.")
   (d/h3 "Basic example")
   (d/p "Default progress bar.")
   (TODO)

   (d/h3 "With label")
   (d/p "Add a " (d/code ":label") " prop to show a visible
   percentage. For low percentages, consider adding
   a" (d/code ":min-width") " to ensure the label's text is fully
   visible.")
   (TODO)

   (d/h3 "Screenreader only label")
   (d/p "Add the " (d/code ":sr-only? true") " option to hide the
   label visually.")
   (TODO)

   (d/h3 "Contextual alternatives")
   (d/p "Progress bars use some of the same button and alert classes
   for consistent styles.")
   (TODO)

   (d/h3 "Striped")
   (d/p "Uses a gradient to create a striped effect. Not available in IE8.")
   (TODO)

   (d/h3 "Animated")
   (d/p "Add the " (d/code ":active? true") " option to animate the
   stripes right to left. Not available in IE9 and below.")
   (TODO)

   (d/h3 "Stacked")
   (d/p "Nest " (d/code "pb/progress-bar") "s to stack them.")
   (TODO)))

;; ## Navs

(defn nav-block []
  (section
   "navs"
   ["Navs " (d/small "nav.cljs")]
   (d/h3 "Example navs")
   (d/p "Navs come in two styles, pills:")
   (->example (slurp-example "nav/pills"))

   (d/p "And tabs:")
   (->example (slurp-example "nav/tabs"))))

;; ## Navbars

(defn navbar-block []
  (section
   "navbars"
   ["Navbars " (d/small "nav.cljs")]
   (d/h3 "Example navbars")
   (->example (slurp-example "nav/bar_basic"))))

;; ## Toggleable Tabs

(defn tab-block []
  (section
   "tabs"
   ["Toggleable tabs " (d/small "(In Progress)")]
   (d/h2 "Example tabs")
   (d/p "Add quick, dynamic tab functionality to transition through
   panes of local content, even via dropdown menus.")

   (d/h3 "Uncontrolled")
   (d/p "Allow the component to control its own state.")
   (TODO)

   (d/h3 "Controlled")
   (d/p "Pass down the active state on render via props.")
   (TODO)

   (d/h3 "No animation")
   (d/p "Set the " (d/code ":animation?") " property to " (d/code "false") ".")
   (TODO)

   (info-callout
    "Extends tabbed navigation"
    ["This plugin extends the "
     (d/a {:href "#navs"} "tabbed navigation component")
     " to add tabbable areas."])))

;; ## Pager

(defn pager-block []
  (section
   "pager"
   ["Pager " (d/small "In Progress")]
   (d/p "Quick previous and next links.")
   (d/h3 "Default")
   (d/p "Centers by default.")
   (TODO)

   (d/h3 "Aligned")
   (d/p "Set the "
        (d/code ":previous?") " or "
        (d/code ":next?") " to "
        (d/code "true") " to align left or right.")
   (TODO)

   (d/h3 "Disabled")
   (d/p "Set the "
        (d/code ":disabled?") " prop to "
        (d/code "true") " to disabled the link.")
   (TODO)))

;; ## Alerts

(defn alert-block []
  (section
   "alerts"
   ["Alert messages " (d/small "random.cljs")]
   (d/h3 "Example alerts")
   (d/p "Basic alert styles.")
   (->example (slurp-example "alert/basic"))

   (d/p "For closeable alerts, just pass an " (d/code ":on-dismiss") "
   function.")
   (TODO)

   (d/p "Auto close after a set time with
   the " (d/code ":dismiss-after") " option.")
   (TODO)))

;; ## Carousels

(defn carousel-block []
  (section
   "carousels"
   ["Carousels " (d/small "In Progress")]
   (d/h2 "Example Carousels")
   (d/h3 "Uncontrolled")
   (d/p "Allow the component to control its own state.")
   (TODO)

   (d/h3 "Controlled")
   (d/p "Pass down the active state on render via props.")
   (TODO)))

;; ## Grids

(defn grid-block []
  (section
   "grids"
   ["Grids " (d/small "grid.cljs")]
   (d/h3 "Example grids")
   (->example (slurp-example "grid"))))

;; ## Labels

(defn label-block []
  (section
   "labels"
   ["Labels " (d/small "random.cljs")]
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
   ["Badges " (d/small "random.cljs")]
   (d/p "Easily highlight new or unread items by adding
   a " (d/code "r/badge") " to links, Bootstrap navs, and more.")
   (d/h3 "Example")
   (->example (slurp-example "badge"))
   (info-callout "Cross-browser compatibility"
                 "Unlike regular Bootstrap, badges self-collapse even
                 in Internet Explorer 8.")))

;; ## Jumbotron

(defn jumbotron-block []
  (section
   "jumbotron"
   ["Jumbotron " (d/small "random.cljs")]
   (d/p "A lightweight, flexible component that can optionally extend
   the entire viewport to showcase key content on your site.")
   (d/h3 "Example")
   (->example (slurp-example "jumbotron/basic"))))

;; ## Page Header

(defn header-block []
  (section
   "page-header"
   ["Page Header " (d/small "random.cljs")]
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
   ["Wells " (d/small "random.cljs")]
   (d/p "Use the well as a simple effect on an element to give it an inset effect.")
   (d/h3 "Default Wells")
   (->example (slurp-example "well/basic"))

   (d/h3 "Optional classes")
   (d/p "Control padding and rounded corners with two optional modifier classes.")
   (->example (slurp-example "well/sizes"))))

;; ## Glyphicons

(defn glyphicon-block []
  (section
   "glyphicons"
   ["Glyphicons " (d/small "random.cljs")]
   (d/p "Use them in buttons, button groups for a toolbar, navigation,
   or prepended form inputs.")
   (d/h3 "Example")
   (->example (slurp-example "glyphicon"))))

;; ## Tables

(defn table-block []
  (section
   "tables"
   ["Tables " (d/small "table.cljs")]
   (d/h3 "Example")
   (d/p "Use the "
        (d/code ":striped? true") ", "
        (d/code ":bordered? true") ", "
        (d/code ":condensed? true") ", and "
        (d/code ":hover? true") " options to customize the table.")
   (->example (slurp-example "table/basic"))

   (d/h3 "Responsive")
   (d/p "Add the " (d/code ":responsive? true") " option to make them
   scroll horizontally up to small devices (under 768px). When viewing
   on anything larger than 768px wide, you will not see any difference
   in these tables.")
   (->example (slurp-example "table/responsive"))))

;; ## Input

(defn input-block []
  (section
   "input"
   ["Input " (d/small "input.cljs")]
   (d/p "Renders an input in bootstrap wrappers. Supports label, help,
   text input add-ons, validation and use as wrapper. om-bootstrap tags the "
        (d/code "input") " node with a " (d/code ":ref") " and " (d/code ":key")
        ", so you can access the internal input element with "
        (d/code "(om/get-node owner \"input\")")
        " as demonstrated in the snippet.")
   (->example (slurp-example "input/validation"))

   (d/h3 "Types")
   (d/p "Supports "
        (d/code "select") ", "
        (d/code "textarea") ", "
        (d/code "static") " as well as the standard HTML input types.")
   (->example (slurp-example "input/types"))

   (d/h3 "Add-ons")
   (d/p "Use " (d/code ":addon-before") "
   and " (d/code ":addon-after") ". Does not support buttons.")
   (->example (slurp-example "input/addons"))

   (d/h3 "Validation")
   (d/p "Set " (d/code ":bs-style") " to one of "
        (d/code "\"success\"") ", "
        (d/code "\"warning\"") ", or"
        (d/code "\"error\"") ". Add "
        (d/code ":has-feedback? true")
        " to show a glyphicon. Glyphicon may need additional styling
        if there is an add-on or no label.")
   (->example (slurp-example "input/feedback"))

   (d/h3 "Horizontal forms")
   (d/p "Use"
        (d/code ":label-classname")
        " and "
        (d/code ":wrapper-classname")
        (d/p " options to add col classes manually. Checkbox and radio
        types need special treatment because label wraps input."))
   (->example (slurp-example "input/horizontal"))

   (d/h3 "Use as a wrapper")
   (d/p "If " (d/code ":type") " is not set, child element(s) will be
   rendered instead of an input element.")
   (->example (slurp-example "input/wrapper"))))

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
           (n/nav-item {:href "#navs"} "Navs")
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

(defn lead []
  (d/div {:class "lead"}
         "This page lists the Om-Bootstrap components. For each component, we provide:"
         (d/ul
          (d/li "Usage instructions")
          (d/li "An example code snippet")
          (d/li "The rendered result of the example snippet"))
         "Click \"show code\" below the rendered component to reveal the snippet."))

(defn components-page []
  [(page-header {:title "Components"})
   (d/div
    {:class "container bs-docs-container"}
    (d/div
     {:class "row"}
     (d/div
      {:class "col-md-9" :role "main"}
      (lead)
      (button-block)
      (panel-block)
      (modal-block)
      (tooltip-block)
      (popover-block)
      (progress-bar-block)
      (nav-block)
      (navbar-block)
      (tab-block)
      (pager-block)
      (alert-block)
      (carousel-block)
      (grid-block)
      (label-block)
      (badge-block)
      (jumbotron-block)
      (header-block)
      (well-block)
      (glyphicon-block)
      (table-block)
      (input-block))
     (sidebar)))])
