package com.example.tests;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.interactions.Actions;

/**
 * Utility class to download videos from YouTube PlayList via Firefox DownLoadHelper plugin
 * Firefox DownLoadHelper plugin can be found @ {@link https://addons.mozilla.org/en-US/firefox/addon/video-downloadhelper/}
 * 
 * <p>
 * Many open source tools exist in the market to download the playlists. Few of them have limitation on the max number of videos in a play list.
 * This program is for fun. An attempt to develop free plugin to download videos from Youtube.
 * <p>
 * Verions used/tested:
 * Selenium WebDriver version : 2.33  {@link http://docs.seleniumhq.org/download/}
 * Firefox: 22.0
 * 
 * 
 * <p>
 * Note the <b>//  --- CHANGE THE VALUE ----</b> comments in the below code. Modify the values appropriately as per your need.
 * </p>
 *  
 * @author Suman Jakkula on 2013/07/14, San Antonio, Texas
 * 
 *
 */
public class YoutubePlayListDownloader {
  
  private WebDriver driver;
  
  //  --- CHANGE THE VALUE ----
  // PlayList URL. Change the value with the required playlist URL
  private static String YOUTUBE_PLAYLIST_URL = "http://www.youtube.com/playlist?list=PL6B08BAA57B5C7810";
  
  // XPath selector to find the list of videos of the playlist. This might change in future. 
  private static String playListVideosXPathSelector ="//div[@class='video-overview yt-grid-fluid']/h3[@class='video-title-container']/a[@class='yt-uix-sessionlink']";
  
  
  @Before
  public void setUp() throws Exception {
	  
	//  --- CHANGE THE VALUE ----
	// Launch the Firefox with the default profile that has DownLoadVideoHelper add-on
	// You many need to change the value of the Path to the firefox on your machine
    driver = new FirefoxDriver(new FirefoxProfile(new File("C:\\Users\\Webi\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\u46cz0u1.default")));
    
    
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  }

  @Test
  public void downLoadVideos() throws Exception {
	 
	  //Launch firefox with the playlist page
    driver.get(YOUTUBE_PLAYLIST_URL);
    
    driver.manage().window().maximize();
    
    // Render contextMenu (i.e right click menu) for each video on playlist and select
    // download option from context menu. This option is rendered because of DownLoadHelper firefox plugin
    
    Actions actions = new Actions(driver);
    List<WebElement> webElements = driver.findElements(By.xpath(playListVideosXPathSelector));
    if ( webElements != null && webElements.isEmpty() == false ) {
    	int videosCount = 0;
    	for ( WebElement element : webElements )
    	{
    		//All the downloads will be queued
    		actions.contextClick(element).sendKeys(Keys.END).sendKeys(Keys.UP).sendKeys(Keys.ENTER).build().perform();
    		videosCount++;
    	}
    	System.err.println("Total videos count = "+videosCount);
    }
  }

  @After
  public void tearDown() throws Exception {
   // driver.quit();
  }
}
