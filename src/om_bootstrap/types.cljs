(ns om-bootstrap.types
  "Types for working with Bootstrap."
  (:require [schema.core :as s])
  (:require-macros [schema.macros :as sm]))

;; ## Schema Utilities

(sm/defn schema-keys
  "Returns all keys from a schema."
  [schema :- {s/Any s/Any}]
  (map (fn [k]
         (if (s/optional-key? k) (:k k) k))
       (keys schema)))

(sm/defn at-least
  "Returns a map schema that accepts the supplied map schema, plus any
  other optional keys that show up in the map. Such a schema can only
  enforce that required keys are missing."
  [schema]
  (assoc schema s/Any s/Any))

;; ## Schema

(def Component
  (s/named s/Any "Alias for an om component, since I don't know what type to put here."))

(def Renderable
  (s/named s/Any "Anything that can get rendered."))

(def class-map
  "Map of keyword to the proper bootstrap class name."
  {"alert" "alert"
   "button" "btn"
   "button-group" "btn-group"
   "button-toolbar" "btn-toolbar"
   "column" "col"
   "input-group" "input-group"
   "form" "form"
   "glyphicon" "glyphicon"
   "label" "label"
   "panel" "panel"
   "panel-group" "panel-group"
   "progress-bar" "progress-bar"
   "nav" "nav"
   "navbar" "navbar"
   "modal" "modal"
   "row" "row"
   "well" "well"})

(def style-map
  "Map of style keywords -> styles."
  {"default" "default"
   "primary" "primary"
   "success" "success"
   "info" "info"
   "warning" "warning"
   "danger" "danger"
   "link" "link"
   "inline" "inline"
   "tabs" "tabs"
   "pills" "pills"})

(def size-map
  {"large" "lg"
   "medium" "md"
   "small" "sm"
   "xsmall" "xs"})

(def BSClass (apply s/enum (keys class-map)))
(def BSStyle (apply s/enum (keys style-map)))
(def BSSize (apply s/enum (keys size-map)))

(def BootstrapClass
  {(s/optional-key :bs-class) BSClass
   (s/optional-key :bs-style) BSStyle
   (s/optional-key :bs-size) BSSize})

(defn bootstrap
  "Applies all default bootstrap options to the supplied schema. If
  the incoming schema has one of the the keys from BootstrapClass,
  that wins (even if it's required)."
  [schema]
  (let [bootstrap-schema (->> (select-keys schema [:bs-class :bs-style :bs-size])
                              (keys)
                              (map s/optional-key)
                              (apply dissoc BootstrapClass))]
    (at-least
     (merge bootstrap-schema schema))))

;; ## Public API
;;
;; Separate follows the best practices set out here:
;; https://gist.github.com/sebmarkbage/a6e220b7097eb3c79ab7

(sm/defn separate :- (s/pair
                      {s/Any s/Any} "om-bootstrap options."
                      {s/Any s/Any} "all other props.")
  "Returns two maps; the first is all of the schema options, the
  second is the REST of the options."
  ([schema opts]
     (separate schema opts {}))
  ([schema opts defaults]
     (let [ks   (set (schema-keys (bootstrap schema)))
           opts (merge defaults opts)]
       [(into {} (filter (comp ks key) opts))
        (into {} (remove (comp ks key) opts))])))

(sm/defn bs-class-set :- {s/Str s/Bool}
  "Returns input for class-set."
  [{:keys [bs-class bs-style bs-size]} :- (at-least BootstrapClass)]
  (if-let [klass (class-map bs-class)]
    (let [prefix (str (name klass) "-")]
      (merge {klass true}
             (when-let [size (size-map bs-size)]
               {(str prefix (name size)) true})
             (when-let [style (style-map bs-style)]
               {(str prefix (name style)) true})))
    {}))
