(ns om-bootstrap.nav
  (:require [clojure.string :as st]
            [om.core :as om]
            [om-bootstrap.types :as t]
            [om-bootstrap.util :as u]
            [om-tools.dom :as d :include-macros true]
            [schema.core :as s])
  (:require-macros [schema.macros :as sm]))

(def NavItem
  (t/bootstrap
   {:title s/Str
    (s/optional-key :on-select) (sm/=> s/Any s/Any)
    (s/optional-key :active?) s/Bool
    (s/optional-key :disabled?) s/Bool
    (s/optional-key :href) s/Str}))

;; TODO: Why can't I put a ref inside the anchor tag without throwing
;; an invariant violation?

(sm/defn nav-item
  "Generates a nav item for use inside of a nav element. It LOOKS like
  Key might be a required thing here."
  [opts :- NavItem & children]
  (let [[bs props] (t/separate NavItem opts {:href "#"})
        classes {:active (:active? bs)
                 :disabled (:disabled? bs)}
        handle-click (fn [e]
                       (when-let [f (:on-select bs)]
                         (.preventDefault e)
                         (when-not (:disabled? bs)
                           (f (:key props)
                              (:href bs)))))]
    (d/li (u/merge-props props {:class (d/class-set classes)})
          (d/a {:href (:href bs)
                :title (:title bs)
                :on-click handle-click}
               children))))

(def Nav
  {:bs-style (s/enum "tabs" "pills")
   (s/optional-key :stacked?) s/Bool
   (s/optional-key :justified?) s/Bool
   (s/optional-key :collapsible?) s/Bool
   (s/optional-key :expanded?) s/Bool
   (s/optional-key :navbar?) s/Bool
   (s/optional-key :pull-right?) s/Bool})

;; NOTE: Compared to the nav in react-bootstrap, this nav doesn't
;; handle setting the navItem property on child dropdown buttons, or
;; work on the activeHref or activeKey property.
;;
;; We're also missing support for the :on-select property, because I
;; can't mutate and propagate the properties on down.
(sm/defn nav
  [opts :- Nav & children]
  (let [[bs opts] (t/separate Nav opts {:bs-class "nav"})]
    (d/nav
     (d/ul {:class (d/class-set
                    (merge (t/bs-class-set bs)
                           {:nav-stacked (:stacked? bs)
                            :nav-justified (:justified? bs)
                            :navbar-nav (:navbar? bs)
                            :pull-right (:pull-right? bs)}))}
           children))))
