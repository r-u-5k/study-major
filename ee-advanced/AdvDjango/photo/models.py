from django.db import models

# Create your models here.
class Photo(models.Model):
    title = models.CharField(max_length=100)
    author = models.CharField(max_length=100)
    image = models.CharField(max_length=100)
    description = models.TextField()
    price = models.IntegerField()
