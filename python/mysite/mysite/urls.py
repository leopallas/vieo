from django.conf.urls import patterns, include, url

from mysite.views import hello, current_datetime, hours_ahead, minutes_ahead, \
    current_datetime2, current_datetime3, \
    current_datetime4, current_datetime5, \
    book_list
# Uncomment the next two lines to enable the admin:
# from django.contrib import admin
# admin.autodiscover()

urlpatterns = patterns('',
    url(r'^hello/$', hello),
    url(r'^time/$', current_datetime),
    url(r'^time/plus/d{1,2}/$', hours_ahead),
    url(r'^time/plus/(\d+)/$', hours_ahead),
)

# urlpatterns = patterns('',
#     (r'^hello/$', hello),
#     (r'^time/$', current_datetime),
#     (r'^time/plus/(\d{1,2})/$', hours_ahead),
#     (r'^time/plus/(\d+)/$', hours_ahead),
#     (r'^time/plus-min/(\d{1,4})/$', minutes_ahead),
#     (r'^time2/$', current_datetime2),
#     (r'^time3/$', current_datetime3),
#     (r'^time4/$', current_datetime4),
#     (r'^time5/$', current_datetime5),
#     (r'^book-list/$', book_list),
# )