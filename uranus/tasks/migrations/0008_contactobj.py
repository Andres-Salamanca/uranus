# Generated by Django 2.1 on 2018-10-06 16:48

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('tasks', '0007_auto_20180925_0645'),
    ]

    operations = [
        migrations.CreateModel(
            name='ContactObj',
            fields=[
                ('id', models.AutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=50)),
                ('email', models.CharField(max_length=50)),
                ('subject', models.CharField(max_length=100)),
                ('message', models.CharField(max_length=200)),
            ],
        ),
    ]
