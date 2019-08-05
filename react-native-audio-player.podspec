require "json"

package = JSON.parse(File.read(File.join(__dir__, "package.json")))

Pod::Spec.new do |s|
  s.name         = "react-native-audio-player"
  s.version      = package["version"]
  s.summary      = package['description']
  s.author       = package['author']
  s.homepage     = package['homepage']
  s.license      = package['license']
  s.ios.deployment_target = "7.0"
  s.tvos.deployment_target = "9.0"
  s.source       = { :git => "https://github.com/liuhong1happy/react-native-audio-player.git", :tag => "v#{s.version}" }
  s.source_files  = "ios/RCTAudioPlayer/*.{h,m}"
  s.frameworks = "AVFoundation"
  s.dependency "React"
end