from django.http import HttpResponse, Http404
import datetime


def hello(request):
    return HttpResponse("Hello World")


def current_datetime(request):
    now = datetime.datetime.now()

    html = "<html><body>It is now %s.</body></html>" % now
    return HttpResponse(html)


def hours_ahead(request, offset):
    try:
        offset = int(offset)
    except ValueError:
        raise Http404()

    dt = datetime.datetime.now() + datetime.timedelta(hours=offset)
    html = "<html><body>In %s hour(s), it will be %s.</body></html>" % (offset, dt)
    return HttpResponse(html)


def minutes_ahead(request, offset):
    try:
        offset = int(offset)
    except ValueError:
        raise Http404()

    dt = datetime.datetime.now() + datetime.timedelta(minutes=offset)
    html = "<html><body>In %s minute(s), it will be %s.</body></html>" % (offset, dt)
    return HttpResponse(html)


from django.template import Template, Context


def current_datetime2(request):
    now = datetime.datetime.now()
    # Simple way of using templates from the filesystem.
    # This is BAD because it doesn't account for missing files!
    fp = open('/home/xw/Workspace/python/mysite/templates/mytemplate.html')
    t = Template(fp.read())
    fp.close()
    html = t.render(Context({'current_date': now}))
    return HttpResponse(html)

from django.template.loader import get_template


def current_datetime3(request):
    now = datetime.datetime.now()
    t = get_template('mytemplate.html')
    html = t.render(Context({'current_date': now}))
    return HttpResponse(html)

from django.shortcuts import render_to_response


def current_datetime4(request):
    now = datetime.datetime.now()
    return render_to_response('mytemplate.html', {'current_date': now})


def current_datetime5(request):
    now = datetime.datetime.now()
    return render_to_response('current_date.html', {'current_date': now})


import MySQLdb


def book_list(request):
    db = MySQLdb.connect(user='root', db='django_db', passwd='root', host='192.168.1.160')
    cursor = db.cursor()
    cursor.execute('SELECT name FROM book ORDER BY name')
    names = [row[0] for row in cursor.fetchall()]
    db.close
    return render_to_response('book_list.html', {'names': names})
