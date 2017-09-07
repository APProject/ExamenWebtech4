from __future__ import unicode_literals

from django.db import models
from mongoengine import *
from recipes.settings import DBNAME
connect(DBNAME)
# Create your models here.
class Recipe(models.Model):
    name = models.CharField(max_length=55)
    calorien = models.TextField()
    ingredienten = models.CharField(max_length=255)
    tijd = models.TimeField()