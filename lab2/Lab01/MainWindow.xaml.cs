﻿using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Drawing;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace Lab01
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        ObservableCollection<Person> people = new ObservableCollection<Person>
        {
            new Person {Name = "P1", Age = 1},
            new Person {Name = "P2", Age = 2}
        };

        public ObservableCollection<Person> Items
        {
            get => people;
        }

        public MainWindow()
        {
            InitializeComponent();
            GetDataTask();
            DataContext = this;
        }


        private void AddImage_Click(object sender, RoutedEventArgs e)
        {
            Microsoft.Win32.OpenFileDialog op = new Microsoft.Win32.OpenFileDialog();

            op.DefaultExt = ".png";
            op.Filter =
                "JPEG Files (*.jpeg)|*.jpeg|PNG Files (*.png)|*.png|JPG Files (*.jpg)|*.jpg|GIF Files (*.gif)|*.gif";

            if (op.ShowDialog() == true)
            {
                image.Source = new BitmapImage(new Uri(op.FileName));
            }
        }

        private void AgeTextBox_TextChanged(object sender, EventArgs e)
        {
            if (System.Text.RegularExpressions.Regex.IsMatch(ageTextBox.Text, "[^0-9]"))
            {
                MessageBox.Show("Błąd! Należy wpisać liczbę");
                ageTextBox.Text = ageTextBox.Text.Remove(ageTextBox.Text.Length - 1);
            }
        }


        private void AddNewPersonButton_Click(object sender, RoutedEventArgs e)
        {
            people.Add(new Person
                {Age = int.Parse(ageTextBox.Text), Name = nameTextBox.Text, Picture = image.Source as BitmapImage});
        }


        private void ListBox_MouseDoubleClick(object sender, EventArgs e)
        {
            Person person = ListBox.SelectedItem as Person;
            nameTextBox.Text = person.Name;
            ageTextBox.Text = person.Age.ToString();
            image.Source = person.Picture;
        }

        private void GetDataTask()
        {
            WebClient client = new WebClient();
            Uri remoteUri1 = new Uri("https://raw.githubusercontent.com/dwyl/english-words/master/words.txt");
            Random random = new Random();


            Task.Run(async () =>
            {
                for (int i=0; i<5; i++)
                {
                    Uri remoteUri = new Uri("https://picsum.photos/200/300/?random" + i.ToString());
                    
                    Dispatcher.Invoke(() => 
                    { 
                        client.DownloadFile(remoteUri, "C:/Users/Kube/Desktop/images/img" + i.ToString() + ".jpg");
                        String htmlText = client.DownloadString(remoteUri1);
                        String[] words = htmlText.Split('\n');
                        String name = words[random.Next(0, words.Length)];
                        int age = random.Next(0, 90);

                        image.Source = new BitmapImage(new Uri("C:/Users/Kube/Desktop/images/img" + i.ToString() + ".jpg"));
                        people.Add(new Person{ Age = age, Name = name, Picture = image.Source as BitmapImage});
                    });

                    MessageBox.Show("Ściągnięto nowy obrazek");
                    await Task.Delay(5000);
                }
            });
        }
    }
}