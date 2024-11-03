from django.urls import path

from . import views

urlpatterns = [
    path('', views.index, name='index'),
    path('registro', views.register, name='registro'),
    path('get-departaments', views.getDepartaments, name='getDepartaments'),
    path('get-cities', views.getCities, name='getCities'),
    path('get-universities', views.getUniversities, name='getUniversities'),
    path('get-schools', views.getSchools, name='getSchools'),
    path('register-action', views.registerAction, name='registerAction'),
    path('introduccion', views.intro, name='introduccion'),
    path('indicaciones', views.tuto, name='indicaciones'),
    path('problema', views.problem, name='problemAction'),
    path('attempt', views.attempt, name='attempt'),
    path('nuevo-problema', views.newProblem, name='newProblemAction'),
    path('contacto', views.contact, name='contacto'),
    path('contact-action', views.contactAction, name='contactAction'),
    path('send-audio-action', views.sendAction, name='sendAction')         
]
