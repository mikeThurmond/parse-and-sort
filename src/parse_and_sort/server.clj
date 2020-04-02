(ns parse-and-sort.server
  (:require
    [parse-and-sort.routes.records :as records]
    [ring.middleware.params :as params]
    [reitit.ring.middleware.muuntaja :as muuntaja]
    [muuntaja.core :as m]
    [reitit.ring.coercion :as coercion]
    [reitit.ring :as ring]))

(def app
  (ring/ring-handler
    (ring/router
      [records/routes]
      {:data {:muuntaja m/instance
              :middleware [params/wrap-params
                           muuntaja/format-middleware
                           coercion/coerce-exceptions-middleware
                           coercion/coerce-request-middleware
                           coercion/coerce-response-middleware]}})
    (ring/create-default-handler)))