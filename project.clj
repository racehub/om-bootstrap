(defproject racehub/om-bootstrap "0.1.0"
  :description "Bootstrap meets Om."
  :url "http://github.com/racehub/om-bootstrap"
  :license {:name "Eclipse Public License - v 1.0"
            :url "http://www.eclipse.org/legal/epl-v10.html"
            :distribution :repo}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2261"]
                 [om "0.6.4"]
                 [prismatic/om-tools "0.2.2"
                  :exclusions [com.keminglabs/cljx
                               org.clojure/clojurescript
                               org.clojure/clojure]]
                 [prismatic/schema "0.2.4"]]
  :plugins [[lein-cljsbuild "1.0.3"]
            [com.cemerick/clojurescript.test "0.3.0"]]
  :profiles {:dev {:dependencies [[prismatic/dommy "0.1.2"]]
                   :plugins [[paddleguru/lein-gitflow "0.1.2"]]
                   :cljsbuild
                   {:builds
                    [{:id "docs"
                      :source-paths ["src" "docs/src"]
                      :compiler {:output-to "docs/main.js"
                                 :output-dir "docs/out"
                                 :optimizations :none}}]}}}
  :lein-release {:deploy-via :shell
                 :shell ["lein" "deploy" "clojars"]})
