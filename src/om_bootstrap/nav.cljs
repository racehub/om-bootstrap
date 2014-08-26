(ns om-bootstrap.nav
  (:require [clojure.string :as st]
            [om.core :as om]
            [om-bootstrap.types :as t]
            [om-bootstrap.util :as u]
            [om-tools.core :refer-macros [defcomponentk]]
            [om-tools.dom :as d :include-macros true]
            [schema.core :as s])
  (:require-macros [schema.macros :as sm]))

;; ## NavItem

(def NavItem
  (t/bootstrap
   {:title s/Str
    (s/optional-key :on-select) (sm/=> s/Any s/Any)
    (s/optional-key :active?) s/Bool
    (s/optional-key :disabled?) s/Bool
    (s/optional-key :href) s/Str}))

(defcomponentk nav-item*
  "Generates a nav item for use inside of a nav element."
  [owner]
  (render
   [_]
   (let [{:keys [opts children]} (om/get-props owner)
         [bs props] (t/separate NavItem opts {:href "#"})
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
                 :ref "anchor"
                 :title (:title bs)
                 :on-click handle-click}
                children)))))

(sm/defn nav-item :- t/Component
  [opts :- NavItem & children]
  (->nav-item* {:opts opts
                :children children}))

;; ## Nav

(def Nav
  (t/bootstrap
   {:bs-style (s/enum "tabs" "pills")
    (s/optional-key :active-key) (s/either s/Str s/Num)
    (s/optional-key :active-href) s/Str
    (s/optional-key :stacked?) s/Bool
    (s/optional-key :justified?) s/Bool
    (s/optional-key :collapsible?) s/Bool
    (s/optional-key :expanded?) s/Bool
    (s/optional-key :navbar?) s/Bool
    (s/optional-key :pull-right?) s/Bool}))

(sm/defn child-active? :- s/Bool
  "Accepts a NavItem's child props and the current options provided to
  the Nav bar; returns true if the child component should be active,
  false otherwise."
  [child-props opts]
  (boolean
   (or (:active? child-props)
       (when-let [ak (:active-key opts)]
         (= ak (:key child-props)))
       (when-let [ak (:active-href opts)]
         (= ak (:href child-props))))))

(sm/defn clone-nav-item
  "Takes the options supplied to the top level nav and returns a
  function that will CLONE the inner nav items, transferring all
  relevant props from the outer code to the inner code."
  [opts]
  (letfn [(prop-fn [props]
            (let [base (-> (select-keys opts [:on-select :active-key :active-href])
                           (assoc :active? (child-active? (:opts props) opts)
                                  :nav-item? true))]
              (update-in props [:opts] u/merge-props base)))]
    (fn [child]
      (u/clone-with-props child prop-fn))))

(sm/defn nav :- t/Component
  [opts :- Nav & children]
  (let [[bs _] (t/separate Nav opts {:bs-class "nav"})]
    (d/nav
     (d/ul {:ref "ul"
            :class (d/class-set
                    (merge (t/bs-class-set bs)
                           {:nav-stacked (:stacked? bs)
                            :nav-justified (:justified? bs)
                            :navbar-nav (:navbar? bs)
                            :pull-right (:pull-right? bs)}))}
           (map (clone-nav-item opts) children)))))
