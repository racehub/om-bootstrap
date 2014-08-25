(ns om-bootstrap.random
  "Components that need to be filed, still."
  (:require [om.core :as om]
            [om-bootstrap.mixins :as m]
            [om-bootstrap.types :as t]
            [om-bootstrap.util :as u]
            [om-tools.core :refer-macros [defcomponentk]]
            [om-tools.dom :as d :include-macros true]
            [schema.core :as s])
  (:require-macros [schema.macros :as sm]))

;; ## Jumbotron

(sm/defn jumbotron :- t/Component
  "A lightweight, flexible component that can optionally extend the
   entire viewport to showcase key content on your site."
  [opts & children]
  (d/div (u/merge-props opts {:class "jumbotron"})
         children))

;; ## Label

(sm/defn label :- t/Component
  "Create a (label {} \"label!\") to show highlight information."
  [opts & children]
  (let [[bs props] (t/separate {} opts {:bs-class "label"
                                        :bs-style "default"})
        classes (t/bs-class-set bs)]
    (d/span (u/merge-props props {:class (d/class-set classes)})
            children)))

;; ## Well

(sm/defn well :- t/Component
  "Use the well as a simple effect on an element to give it an inset effect."
  [opts & children]
  (let [[bs props] (t/separate {} opts {:bs-class "well"})
        class (d/class-set (t/bs-class-set bs))]
    (d/div (u/merge-props props {:class class})
           children)))

;; ## Header

(sm/defn page-header :- t/Component
  "A simple shell for an h1 to appropriately space out and segment
  sections of content on a page. It can utilize the h1’s default small
  element, as well as most other components (with additional styles)."
  [opts & children]
  (d/div (u/merge-props opts {:class "page-header"})
         (d/h1 children)))

;; ## Tooltip

(def Placement
  (s/enum "top" "right" "bottom" "left"))

(def ToolTip
  (t/bootstrap
   {:placement Placement
    (s/optional-key :position-left) s/Int
    (s/optional-key :position-top) s/Int
    (s/optional-key :arrow-offset-left) s/Int
    (s/optional-key :arrow-offset-top) s/Int}))

(sm/defn tooltip :- t/Component
  [opts :- ToolTip & children]
  (let [[bs _] (t/separate ToolTip opts {:placement "right"})
        classes {:tooltip true
                 (:placement bs) true
                 :in (or (:position-left bs)
                         (:position-top bs))}]
    (d/div {:class (d/class-set classes)
            :style {:left (:position-left bs)
                    :top (:position-top bs)}}
           (d/div {:class "tooltip-arrow"
                   :style {:left (:arrow-offset-left bs)
                           :top (:arrow-offset-top bs)}})
           (d/div {:class "tooltip-inner"}
                  children))))

;; ## Alert

(def Alert
  (t/bootstrap
   {:on-dismiss (sm/=> s/Any s/Any)
    :dismiss-after s/Int}))

(def alert-defaults
  {:bs-class "alert" :bs-style "info"})

(defcomponentk alert*
  "Renders the alert component with timeout mixed in. TODO: This
   should probably use the component macro and be defined inline under
   the alert function. No need for a separate name."
  [[:data bs props children] owner]
  (:mixins m/set-timeout-mixin)
  (did-mount [_] (when (and (:on-dismiss bs) (:dismiss-after bs))
                   (doto owner
                     (.set-timeout (:on-dismiss bs)
                                   (:dismiss-after bs)))))
  (render
   [_]
   (let [classes (t/bs-class-set bs)
         dismiss-button (when-let [od (:on-dismiss bs)]
                          (d/button {:type "button"
                                     :class "close"
                                     :on-click od
                                     :aria-hidden true}
                                    "&times;"))]
     (d/div (u/merge-props props {:class (d/class-set classes)})
            dismiss-button
            children))))

(sm/defn alert :- t/Component
  "Wrapper for the alert component to allow a better user interface."
  [opts :- Alert & children]
  (let [[bs props] (t/separate Alert opts alert-defaults)]
    (om/build alert* {:bs bs
                      :props props
                      :children children})))

;; ## Popover

(def Popover
  (t/bootstrap
   {(s/optional-key :title) t/Renderable
    (s/optional-key :placement) Placement
    (s/optional-key :position-left) s/Int
    (s/optional-key :position-top) s/Int
    (s/optional-key :arrow-offset-left) s/Int
    (s/optional-key :arrow-offset-top) s/Int}))

;; TODO: Abstract out shared style generation between here and
;; tooltip.
(sm/defn popover :- t/Component
  [opts :- Popover & children]
  (let [[bs _] (t/separate Popover opts {:placement "right"})
        classes {:popover true
                 (:placement bs) true
                 :in (or (:position-left bs)
                         (:position-top bs))}]
    (d/div {:class (d/class-set classes)
            :style {:left (:position-left bs)
                    :top (:position-top bs)
                    :display "block"}}
           (d/div {:class "arrow"
                   :style {:left (:arrow-offset-left bs)
                           :top (:arrow-offset-top bs)}})
           (when-let [title (:title bs)]
             (d/h3 {:class "popover-title"} title))
           (d/div {:class "popover-content"}
                  children))))

;; ## Badge

(def Badge
  (t/bootstrap
   {(s/optional-key :pull-right?) s/Bool}))

(sm/defn badge :- t/Component
  [opts :- Badge & children]
  (let [[bs props] (t/separate Badge opts)
        classes {:pull-right (:pull-right? bs)
                 :badge (u/some-valid-component? children)}]
    (d/span (u/merge-props props {:class (d/class-set classes)})
            children)))

;; ## Glyphicon

(def Glyphicon
  (t/bootstrap {:glyph s/Str}))

(sm/defn glyphicon :- t/Component
  [opts :- Glyphicon & children]
  (let [[bs props] (t/separate Glyphicon opts {:bs-class "glyphicon"})
        classes (assoc (t/bs-class-set bs)
                  (str "glyphicon-" (:glyph bs)) true)]
    (d/span (u/merge-props props {:class (d/class-set classes)})
            children)))
