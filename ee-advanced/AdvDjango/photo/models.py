from django.db import models

# Create your models here.
class Photo(models.Model):
    title = models.CharField(max_length=100)
    author = models.CharField(max_length=100)
    image = models.ImageField(upload_to='photos/%Y/%m/%d/')
    description = models.TextField()
    price = models.IntegerField()
