# from django.conf import settings
# settings.configure()
import os

os.environ.setdefault("DJANGO_SETTINGS_MODULE", "mysite.settings")

from django.template import Template, Context


t = Template('My name is {{ name }}')
# try:
#     t = template.Template('{% notatag %}')
#     print t
# except template.base.TemplateSyntaxError:
print t

c = Context({'name': 'xuwei'})
print t.render(c)

raw_template = """
<p>Dear {{ person_name }},</p>
<p>Thanks for placing an order from {{ company }}. It's scheduled to ship on {{ ship_date|date:"F j, Y" }}.</p>

{% if ordered_warranty %}
<p>Your warranty information will be included in the packaging.</p>
{% else %}
<p>You didn't order a warranty, so you're on your own when
the products inevitably stop working.</p>
{% endif %}

<p>Sincerely,<br />{{ company }}</p>
"""

t = Template(raw_template)

import datetime

c = Context({'person_name': 'Leo',
             'company': 'Google',
             'ship_date': datetime.date(2013, 4, 17),
             'ordered_warranty': False})

x = t.render(c)
print t.render(c)

person = {'name': 'Sally', 'age': '43'}
t = Template('{{ person.name }} is {{ person.age }} years old.')
c = Context({'person': person})
print t.render(c)


d = datetime.date(1993, 5, 2)
t = Template('The month is {{ date.month }} and the year is {{ date.year }}.')
c = Context({'date': d})
print t.render(c)

class Person(object):
    def __init__(self, first_name, last_name):
        self.first_name, self.last_name = first_name, last_name


t = Template('Hello, {{ person.first_name }} {{ person.last_name }}.')

c = Context({'person': Person('John', 'Smith')})
print t.render(c)

t = Template('{{ var }} -- {{ var.upper }} -- {{ var.isdigit }}')
print t.render(Context({'var': 'hello'}))

print t.render(Context({'var': '123'}))

t = Template('Item 2 is {{ items.2 }}.')
c = Context({'items': ['apples', 'bananas', 'carrots']})
print t.render(c)

person = {'name': 'Sally', 'age': '43'}
t = Template('{{ person.name.upper }} is {{ person.age }} years old.')
c = Context({'person': person})
print t.render(c)


t = Template("My name is {{ person.first_name }}.")


class PersonClass3:
    def first_name(self):
        raise AssertionError,"foo"

p = PersonClass3
# print t.render(Context({"person": p}))


class SilentAssertionError(AssertionError):
    silent_variable_failure = True


class PersonClass4:
    def first_name(self):
        raise SilentAssertionError


p = PersonClass4()
print t.render(Context({"person": p}))

c = Context({"foo": "bar"})
print c['foo']

del c['foo']

# print c['foo']

c['newvariable'] = 'hello'
print c['newvariable']