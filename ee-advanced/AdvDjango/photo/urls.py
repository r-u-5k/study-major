from django.urls import path
from photo import views

urlpatterns = [
    path('', views.photo_list, name='photo_list'),
    path('<int:pk>/', views.photo_detail, name='photo_detail'),
    path('new/', views.photo_post, name='photo_post'),
    path('<int:pk>/edit/', views.photo_edit, name='photo_edit'),]