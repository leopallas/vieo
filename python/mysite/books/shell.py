__author__ = 'leo'

import os

os.environ.setdefault("DJANGO_SETTINGS_MODULE", "mysite.settings")

from books.models import Publisher

p1 = Publisher(name='Apress', address='2855 Telegraph Avenue',
               city='Berkeley', state_province='CA', country='U.S.A.',
               website='http://www.apress.com/')
p1.save()

p2 = Publisher(name="O'Reilly", address='10 Fawcett St.',
               city='Cambridge', state_province='MA', country='U.S.A.',
               website='http://www.oreilly.com/')
p2.save()







publisher_list = Publisher.objects.all()