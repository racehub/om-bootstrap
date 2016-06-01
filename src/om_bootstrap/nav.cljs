(ns om-bootstrap.nav
  (:require [clojure.string :as st]
            [om.core :as om]
            [om-bootstrap.types :as t]
            [om-bootstrap.util :as u]
            [om-tools.core :refer-macros [defcomponentk]]
            [om-tools.dom :as d :include-macros true]
            [schema.core :as s :include-macros true]))

;; ## NavItem

(def NavItem
  (t/bootstrap
   {(s/optional-key :title) s/Str
    (s/optional-key :on-select) (s/=> s/Any s/Any)
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

(s/defn nav-item :- t/Component
  [opts :- NavItem & children]
  (->nav-item* {:opts opts
                :children children}))

;; ## Nav

(def Nav
  (t/bootstrap
   {:bs-style (s/enum "tabs" "pills")
    (s/optional-key :active-key) (s/cond-pre s/Str s/Num)
    (s/optional-key :active-href) s/Str
    (s/optional-key :stacked?) s/Bool
    (s/optional-key :justified?) s/Bool
    (s/optional-key :collapsible?) s/Bool
    (s/optional-key :expanded?) s/Bool
    (s/optional-key :navbar?) s/Bool
    (s/optional-key :pull-right?) s/Bool}))

(s/defn child-active? :- s/Bool
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

(s/defn clone-nav-item
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

(defcomponentk nav* [owner]
  (render
   [_]
   (let [{:keys [opts children]} (om/get-props owner)
         [bs props] (t/separate Nav opts {:expanded? true
                                          :bs-class "nav"})
         classes {:navbar-collapse (:collapsible? bs)
                  :collapse (not (:expanded? bs))
                  :in (:expanded? bs)}]
     (if (and (:navbar? bs)
              (not (:collapsible? bs)))
       (let [children (map (clone-nav-item opts) children)
             ul-props {:ref "ul"
                       :class (d/class-set
                               (merge (t/bs-class-set bs)
                                      {:nav-stacked (:stacked? bs)
                                       :nav-justified (:justified? bs)
                                       :navbar-nav (:navbar? bs)
                                       :pull-right (:pull-right? bs)}))}]
         (d/ul (u/merge-props props ul-props) children))
       (let [ul-props-left {:ref "ul"
                            :class (d/class-set
                                    (merge (t/bs-class-set bs)
                                           {:nav-stacked (:stacked? bs)
                                            :nav-justified (:justified? bs)
                                            :navbar-nav (:navbar? bs)
                                            :pull-right false}))}
             ul-props-right
             {:ref "ul"
              :class (d/class-set
                      (merge (t/bs-class-set bs)
                             {:nav-stacked (:stacked? bs)
                              :nav-justified (:justified? bs)
                              :navbar-nav (:navbar? bs)
                              :pull-right true}))}
             [children-left _ children-right] (partition-by #(= :right %) children)
             children-left (map (clone-nav-item opts) children-left)
             children-right (map (clone-nav-item opts) children-right)]
         (d/nav (u/merge-props props {:class (d/class-set classes)})
                (if (not (empty? children-left)) (d/ul ul-props-left children-left))
                (if (not (empty? children-right))(d/ul ul-props-right children-right))))))))

(s/defn nav :- t/Component
  [opts :- Nav & children]
  (->nav* {:opts opts
           :children children}))

;; ## SubNav


;; ## NavBar

(def NavBar
  (t/bootstrap
   {(s/optional-key :component-fn) (s/=> s/Any s/Any)
    (s/optional-key :fixed-top?) s/Bool
    (s/optional-key :fixed-bottom?) s/Bool
    (s/optional-key :static-top?) s/Bool
    (s/optional-key :inverse?) s/Bool
    (s/optional-key :role) s/Str
    (s/optional-key :brand) t/Renderable
    (s/optional-key :on-toggle) (s/=> s/Any s/Any)
    (s/optional-key :toggle-nav-key) s/Str
    (s/optional-key :nav-expanded?) s/Bool
    (s/optional-key :default-nav-expanded?) s/Bool}))

(defn render-toggle-button [owner bs]
  (let [handle-toggle (fn []
                        (when-let [f (:on-toggle bs)]
                          (om/set-state-nr! owner [:changing?] true)
                          (f)
                          (om/set-state-nr! owner [:changing?] false))
                        (om/update-state! owner [:nav-open?] not))
        tb (u/clone-with-props (:toggle-button bs)
                               {:class "navbar-toggle"
                                :on-click handle-toggle})]
    (d/button {:class "navbar-toggle"
               :type "button"
               :on-click handle-toggle}
              (or tb [(d/span {:class "sr-only" :key 0} "Toggle navigation")
                      (d/span {:class "icon-bar" :key 1})
                      (d/span {:class "icon-bar" :key 2})
                      (d/span {:class "icon-bar" :key 3})]))))

(s/defn render-header-and-toggle-btn? :- s/Bool
  "Returns true if any of the necessary properties are in place to
  render the navbar-header and toggle button."
  [bs]
  (boolean
    (or (:brand bs)
        (:toggle-button bs)
        (:toggle-nav-key bs))))

(defn render-header [owner bs]
  (d/div {:class "navbar-header"}
         (if (u/strict-valid-component? (:brand bs))
           (u/clone-with-props (:brand bs) {:class "navbar-brand"})
           (d/span {:class "navbar-brand"} (:brand bs)))
         (when (render-header-and-toggle-btn? bs)
           (render-toggle-button owner bs))))

(defn render-navbar-child [owner child bs]
  (let [f (fn [props]
            (let [opts (:opts props)
                  collapsible? (or (:collapsible? opts)
                                   (when (:toggle-nav-key bs)
                                     (= (:key opts) (:toggle-nav-key bs))))
                  base {:navbar? true
                        :collapsible? collapsible?
                        :expanded? (and collapsible?
                                        (or (:nav-expanded? bs)
                                            (om/get-state owner :nav-open?)))}]
              (update-in props [:opts] u/merge-props base)))]
    (u/clone-with-props child f)))

(defcomponentk navbar*
  [[:data opts children] owner]
  (init-state [_] {:nav-open? (:default-nav-expanded? opts)
                   :changing? false})
  (should-update [_ _ next-state]
                 (not (:changing? next-state)))
  (render
   [_]
   (let [[bs props] (t/separate NavBar opts
                                {:bs-class "navbar"
                                 :bs-style "default"
                                 :role "navigation"
                                 :component-fn (fn [opts & c]
                                                 (d/nav opts c))})
         classes (assoc (t/bs-class-set bs)
                   :navbar-fixed-top (:fixed-top? bs)
                   :navbar-fixed-bottom (:fixed-bottom? bs)
                   :navbar-static-top (:static-top? bs)
                   :navbar-inverse (:inverse? bs))]
     ((:component-fn bs) (u/merge-props (merge bs props)
                                        {:class (d/class-set classes)})
      (d/div {:class (if (:fluid props) "container-fluid" "container")}
             (when (render-header-and-toggle-btn? bs)
               (render-header owner bs))
             (map #(render-navbar-child owner % bs) children))))))

(s/defn navbar
  [opts :- NavBar & children]
  (->navbar* {:opts opts
              :children children}))
